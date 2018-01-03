package org.latheild.project.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.api.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;
import org.latheild.project.api.constant.ProjectErrorCode;
import org.latheild.project.api.dto.ChangeOwnerDTO;
import org.latheild.project.api.dto.ProjectDTO;
import org.latheild.project.client.UserClient;
import org.latheild.project.constant.DAOQueryMode;
import org.latheild.project.dao.ProjectRepository;
import org.latheild.project.domain.Project;
import org.latheild.project.utils.TutorialProjectCreator;
import org.latheild.user.api.constant.UserErrorCode;
import org.latheild.user.api.dto.RegisterDTO;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQExchange.PROJECT_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.PROJECT_QUEUE;

@Service
@RabbitListener(queues = PROJECT_QUEUE)
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserClient userClient;

    @Autowired
    ProjectRepository projectRepository;

    private boolean isProjectExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                if (projectRepository.countById(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            case QUERY_BY_USER_ID:
                if (projectRepository.countByOwnerId(target) > 0) {
                    return true;
                } else {
                    return false;
                }
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private ProjectDTO convertFromProjectToProjectDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setDescription(project.getDescription());
        projectDTO.setOwnerId(project.getOwnerId());
        projectDTO.setName(project.getName());
        projectDTO.setProjectId(project.getId());
        return projectDTO;
    }

    private ArrayList<ProjectDTO> convertFromProjectsToProjectDTOs(ArrayList<Project> projects) {
        ArrayList<ProjectDTO> projectDTOs = new ArrayList<ProjectDTO>();
        for (Project project : projects) {
            projectDTOs.add(convertFromProjectToProjectDTO(project));
        }
        return projectDTOs;
    }

    private Project convertFromProjectDTOToProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setDescription(projectDTO.getDescription());
        project.setName(projectDTO.getName());
        project.setOwnerId(projectDTO.getOwnerId());
        return project;
    }

    @RabbitHandler
    public void eventHandler(Message message) {
        switch (message.getMessageType()) {
            case USER_CREATED:
                RegisterDTO registerDTO = (RegisterDTO) message.getMessageBody();

                Project project = convertFromProjectDTOToProject(
                        TutorialProjectCreator.setTutorialProject(registerDTO.getUserId())
                );
                projectRepository.save(project);

                rabbitTemplate.convertAndSend(
                        PROJECT_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.TUTORIAL_PROJECT_CREATED,
                                convertFromProjectToProjectDTO(project)
                        )
                );
                break;
            case USER_DELETED:
                String messageBody = (String) message.getMessageBody();
                if (message.equals(ADMIN_DELETE_ALL)) {
                    adminDeleteAllProjects(ADMIN_CODE);
                } else {
                    if (isProjectExist(DAOQueryMode.QUERY_BY_USER_ID, messageBody)) {
                        projectRepository.deleteAllByOwnerId(messageBody);
                    }/* else {
                        throw new AppBusinessException(
                                ProjectErrorCode.ProjectNotExist,
                                String.format("User %s does not have any project", message)
                        );
                    }*/
                }
                break;
        }
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        if (userClient.checkUserExistance(projectDTO.getOwnerId())) {
            Project project = convertFromProjectDTOToProject(projectDTO);
            projectRepository.save(project);
            projectDTO.setProjectId(project.getId());
            return projectDTO;
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s does not exist", projectDTO.getOwnerId())
            );
        }
    }

    @Override
    public ProjectDTO updateProjectInfo(ProjectDTO projectDTO) {
        if (isProjectExist(DAOQueryMode.QUERY_BY_ID, projectDTO.getProjectId())) {
            Project project = projectRepository.findById(projectDTO.getProjectId());
            if (project.getOwnerId().equals(projectDTO.getOwnerId())) {
                project.setDescription(projectDTO.getDescription());
                project.setName(projectDTO.getName());
                projectRepository.save(project);
                return projectDTO;
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.ProjectNotExist,
                    String.format("Project %s does not exist", projectDTO.getProjectId())
            );
        }
    }

    @Override
    public ProjectDTO changeProjectOwner(ChangeOwnerDTO changeOwnerDTO) {
        if (isProjectExist(DAOQueryMode.QUERY_BY_ID, changeOwnerDTO.getProjectId())) {
            if (changeOwnerDTO.getNewOwnerId() == null || changeOwnerDTO.getOldOwnerId() == null) {
                throw new AppBusinessException(
                        CommonErrorCode.INVALID_ARGUMENT
                );
            }
            if (userClient.checkUserExistance(changeOwnerDTO.getOldOwnerId())) {
                if (userClient.checkUserExistance(changeOwnerDTO.getNewOwnerId())) {
                    Project project = projectRepository.findById(changeOwnerDTO.getProjectId());
                    if (project.getOwnerId().equals(changeOwnerDTO.getOldOwnerId())) {
                        project.setOwnerId(changeOwnerDTO.getNewOwnerId());
                        projectRepository.save(project);
                        return convertFromProjectToProjectDTO(project);
                    } else {
                        throw new AppBusinessException(
                                CommonErrorCode.UNAUTHORIZED
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            UserErrorCode.UserNotExist,
                            String.format("User %s does not exist", changeOwnerDTO.getNewOwnerId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        UserErrorCode.UserNotExist,
                        String.format("User %s does not exist", changeOwnerDTO.getOldOwnerId())
                );
            }
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.ProjectNotExist,
                    String.format("Project %s does not exist", changeOwnerDTO.getProjectId())
            );
        }
    }

    @Override
    public void deleteProjectById(ProjectDTO projectDTO) {
        if (isProjectExist(DAOQueryMode.QUERY_BY_ID, projectDTO.getProjectId())) {
            Project project = projectRepository.findById(projectDTO.getProjectId());
            if (project.getOwnerId().equals(projectDTO.getOwnerId())) {
                projectRepository.deleteById(projectDTO.getProjectId());
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.ProjectNotExist,
                    String.format("Project %s does not exist", projectDTO.getProjectId())
            );
        }
    }

    @Override
    public ProjectDTO getProjectById(String id) {
        if (isProjectExist(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromProjectToProjectDTO(projectRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.ProjectNotExist,
                    String.format("Project %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<ProjectDTO> getProjectsByOwnerId(String ownerId) {
        if (isProjectExist(DAOQueryMode.QUERY_BY_USER_ID, ownerId)) {
            return convertFromProjectsToProjectDTOs(projectRepository.findAllByOwnerId(ownerId));
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.ProjectNotExist,
                    String.format("Project %s does not exist", ownerId)
            );
        }
    }

    @Override
    public ArrayList<ProjectDTO> adminGetAllProjects(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (projectRepository.count() > 0) {
                return convertFromProjectsToProjectDTOs(projectRepository.findAll());
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.ProjectNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteProjectById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isProjectExist(DAOQueryMode.QUERY_BY_ID, id)) {
                projectRepository.deleteById(id);
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.ProjectNotExist,
                        String.format("Project %s does not exist", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteProjectsByOwnerId(String ownerId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isProjectExist(DAOQueryMode.QUERY_BY_USER_ID, ownerId)) {
                projectRepository.deleteAllByOwnerId(ownerId);
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.ProjectNotExist,
                        String.format("Project %s does not exist", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllProjects(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (projectRepository.count() > 0) {
                projectRepository.deleteAll();
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.ProjectNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}

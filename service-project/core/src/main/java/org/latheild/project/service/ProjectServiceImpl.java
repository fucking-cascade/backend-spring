package org.latheild.project.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.api.CommonIdentityType;
import org.latheild.common.utils.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;
import org.latheild.project.api.constant.ProjectErrorCode;
import org.latheild.project.api.dto.ProjectMemberOperationDTO;
import org.latheild.project.api.dto.ChangeOwnerDTO;
import org.latheild.project.api.dto.ProjectDTO;
import org.latheild.project.client.RelationClient;
import org.latheild.project.client.UserClient;
import org.latheild.project.constant.DAOQueryMode;
import org.latheild.project.dao.ProjectRepository;
import org.latheild.project.domain.Project;
import org.latheild.project.utils.TutorialProjectCreator;
import org.latheild.relation.api.dto.RelationDTO;
import org.latheild.relation.api.utils.RelationDTOAnalyzer;
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
    RelationClient relationClient;

    @Autowired
    UserClient userClient;

    @Autowired
    ProjectRepository projectRepository;

    private boolean isProjectExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (projectRepository.countById(target) > 0);
            case QUERY_BY_USER_ID:
                return (projectRepository.countByOwnerId(target) > 0);
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
        ArrayList<ProjectDTO> projectDTOs = new ArrayList<>();
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
                //relationClient.addProjectMember(project.getOwnerId(), project.getId());

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
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (projectRepository.count() > 0) {
                        adminDeleteAllProjects(ADMIN_CODE);
                    }
                } else {
                    if (isProjectExist(DAOQueryMode.QUERY_BY_USER_ID, messageBody)) {
                        projectRepository.deleteAllByOwnerId(messageBody);
                    }
                }
                break;
            default:
                System.out.println(message.toString());
        }
    }

    @Override
    public boolean checkProjectExistence(String projectId) {
        return isProjectExist(DAOQueryMode.QUERY_BY_ID, projectId);
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        if (userClient.checkUserExistence(projectDTO.getOwnerId())) {
            Project project = convertFromProjectDTOToProject(projectDTO);
            projectRepository.save(project);
            //relationClient.addProjectMember(project.getOwnerId(), project.getId());
            return convertFromProjectToProjectDTO(project);
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
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
                return convertFromProjectToProjectDTO(project);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.PROJECT_NOT_EXIST,
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
            if (userClient.checkUserExistence(changeOwnerDTO.getOldOwnerId())) {
                if (userClient.checkUserExistence(changeOwnerDTO.getNewOwnerId())) {
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
                            UserErrorCode.USER_NOT_EXIST,
                            String.format("User %s does not exist", changeOwnerDTO.getNewOwnerId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        UserErrorCode.USER_NOT_EXIST,
                        String.format("User %s does not exist", changeOwnerDTO.getOldOwnerId())
                );
            }
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.PROJECT_NOT_EXIST,
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

                rabbitTemplate.convertAndSend(
                        PROJECT_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.PROJECT_DELETED,
                                projectDTO.getProjectId()
                        )
                );
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProjectErrorCode.PROJECT_NOT_EXIST,
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
                    ProjectErrorCode.PROJECT_NOT_EXIST,
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
                    ProjectErrorCode.PROJECT_NOT_EXIST,
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
                        ProjectErrorCode.PROJECT_NOT_EXIST
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

                rabbitTemplate.convertAndSend(
                        PROJECT_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.PROJECT_DELETED,
                                id
                        )
                );
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST,
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
                ArrayList<Project> projects = projectRepository.findAllByOwnerId(ownerId);
                projectRepository.deleteAllByOwnerId(ownerId);

                for (Project project : projects) {
                    rabbitTemplate.convertAndSend(
                            PROJECT_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.PROJECT_DELETED,
                                    project.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST,
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

                rabbitTemplate.convertAndSend(
                        PROJECT_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.PROJECT_DELETED,
                                ADMIN_DELETE_ALL
                        )
                );
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void addProjectMember(ProjectMemberOperationDTO projectMemberOperationDTO) {
        if (userClient.checkUserExistence(projectMemberOperationDTO.getExecutorId())) {
            if (userClient.checkUserExistence(projectMemberOperationDTO.getMemberId())) {
                if (isProjectExist(DAOQueryMode.QUERY_BY_ID, projectMemberOperationDTO.getProjectId())) {
                    Project project = projectRepository.findById(projectMemberOperationDTO.getProjectId());
                    if (project.getOwnerId().equals(projectMemberOperationDTO.getExecutorId())) {
                        relationClient.addProjectMember(
                                projectMemberOperationDTO.getMemberId(),
                                projectMemberOperationDTO.getProjectId()
                        );
                    } else {
                        throw new AppBusinessException(
                                CommonErrorCode.UNAUTHORIZED
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            ProjectErrorCode.PROJECT_NOT_EXIST,
                            String.format("Project %s does not exist", projectMemberOperationDTO.getProjectId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        UserErrorCode.USER_NOT_EXIST,
                        String.format("User %s does not exist", projectMemberOperationDTO.getMemberId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", projectMemberOperationDTO.getExecutorId())
            );
        }
    }

    @Override
    public void removeProjectMember(ProjectMemberOperationDTO projectMemberOperationDTO) {
        if (userClient.checkUserExistence(projectMemberOperationDTO.getExecutorId())) {
            if (userClient.checkUserExistence(projectMemberOperationDTO.getMemberId())) {
                if (isProjectExist(DAOQueryMode.QUERY_BY_ID, projectMemberOperationDTO.getProjectId())) {
                    Project project = projectRepository.findById(projectMemberOperationDTO.getProjectId());
                    if (project.getOwnerId().equals(projectMemberOperationDTO.getExecutorId())) {
                        relationClient.deleteProjectMember(
                                projectMemberOperationDTO.getMemberId(),
                                projectMemberOperationDTO.getProjectId()
                        );
                    } else {
                        throw new AppBusinessException(
                                CommonErrorCode.UNAUTHORIZED
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            ProjectErrorCode.PROJECT_NOT_EXIST,
                            String.format("Project %s does not exist", projectMemberOperationDTO.getProjectId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        UserErrorCode.USER_NOT_EXIST,
                        String.format("User %s does not exist", projectMemberOperationDTO.getMemberId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", projectMemberOperationDTO.getExecutorId())
            );
        }
    }

    @Override
    public ArrayList<ProjectDTO> getAllProjectsByUserId(String userId) {
        try {
            ArrayList<RelationDTO> relationDTOs = relationClient.getUserProjects(userId);
            ArrayList<ProjectDTO> projectDTOs = new ArrayList<>();
            ProjectDTO projectDTO;
            for (RelationDTO relationDTO : relationDTOs) {
                projectDTO = getProjectById(RelationDTOAnalyzer.getProjectId(relationDTO));
                projectDTOs.add(projectDTO);
            }
            return projectDTOs;
        } catch (Exception e) {
            throw new AppBusinessException(
                    CommonErrorCode.INTERNAL_ERROR,
                    e.getMessage()
            );
        }
    }
}

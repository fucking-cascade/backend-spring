package org.latheild.progress.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.utils.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;
import org.latheild.progress.api.constant.ProgressErrorCode;
import org.latheild.progress.api.dto.ProgressDTO;
import org.latheild.progress.client.ProjectClient;
import org.latheild.progress.client.UserClient;
import org.latheild.progress.constant.DAOQueryMode;
import org.latheild.progress.dao.ProgressRepository;
import org.latheild.progress.domain.Progress;
import org.latheild.progress.utils.TutorialProgressListCreator;
import org.latheild.project.api.constant.ProjectErrorCode;
import org.latheild.project.api.dto.ProjectDTO;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQExchange.PROGRESS_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.PROGRESS_QUEUE;

@Service
@RabbitListener(queues = PROGRESS_QUEUE)
public class ProgressServiceImpl implements ProgressService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserClient userClient;

    @Autowired
    ProjectClient projectClient;

    @Autowired
    ProgressRepository progressRepository;

    private boolean isProgressExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (progressRepository.countById(target) > 0);
            case QUERY_BY_OWNER_ID:
                return (progressRepository.countByOwnerId(target) > 0);
            case QUERY_BY_PROJECT_ID:
                return (progressRepository.countByProjectId(target) > 0);
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private ProgressDTO convertFromProgressToProgressDTO(Progress progress) {
        ProgressDTO progressDTO = new ProgressDTO();
        progressDTO.setName(progress.getName());
        progressDTO.setOrder(progress.getOrder());
        progressDTO.setOwnerId(progress.getOwnerId());
        progressDTO.setProjectId(progress.getProjectId());
        progressDTO.setProgressId(progress.getId());
        return progressDTO;
    }

    private ArrayList<ProgressDTO> convertFromProgressListToProgressDTOs(ArrayList<Progress> progressList) {
        ArrayList<ProgressDTO> progressDTOs = new ArrayList<>();
        for (Progress progress : progressList) {
            progressDTOs.add(convertFromProgressToProgressDTO(progress));
        }
        return progressDTOs;
    }

    private Progress convertFromProgressDTOToProgress(ProgressDTO progressDTO) {
        Progress progress = new Progress();
        progress.setName(progressDTO.getName());
        progress.setOrder(progressDTO.getOrder());
        progress.setOwnerId(progressDTO.getOwnerId());
        progress.setProjectId(progressDTO.getProjectId());
        return progress;
    }

    private ArrayList<Progress> convertFromProgressDTOsToProgressList(ArrayList<ProgressDTO> progressDTOs) {
        ArrayList<Progress> progressList = new ArrayList<>();
        for (ProgressDTO progressDTO : progressDTOs) {
            progressList.add(convertFromProgressDTOToProgress(progressDTO));
        }
        return progressList;
    }

    private ProgressDTO adjustIndex(ProgressDTO progressDTO) {
        if (progressDTO.getOrder() >= progressRepository.countByProjectId(progressDTO.getProjectId())) {
            progressDTO.setOrder(progressRepository.countByProjectId(progressDTO.getProjectId()));
        } else if (progressDTO.getOrder() < 0) {
            progressDTO.setOrder(0);
        }
        return progressDTO;
    }

    private void resetIndexForProgressListOfProject(Progress progress) {
        ArrayList<Progress> progressList = progressRepository.findAllByProjectIdOrderByIndexAsc(progress.getProjectId());
        int i = 0;
        for (Progress iter : progressList) {
            iter.setOrder(i);
            i++;
            progressRepository.save(iter);
        }
    }

    @RabbitHandler
    public void eventHandler(Message message) {
        switch (message.getMessageType()) {
            case TUTORIAL_PROJECT_CREATED:
                ProjectDTO projectDTO = (ProjectDTO) message.getMessageBody();

                ArrayList<Progress> progressList = convertFromProgressDTOsToProgressList(
                        TutorialProgressListCreator.setTutorialProgressList(projectDTO.getOwnerId(), projectDTO.getProjectId())
                );
                for (Progress progress : progressList) {
                    progress.setOrder(progressRepository.countByProjectId(progress.getProjectId()));
                    progressRepository.save(progress);

                    rabbitTemplate.convertAndSend(
                            PROGRESS_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.TUTORIAL_PROGRESS_CREATED,
                                    convertFromProgressToProgressDTO(progress)
                            )
                    );
                }
                break;
            case PROJECT_DELETED:
                String messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (progressRepository.count() > 0) {
                        adminDeleteAllProgress(ADMIN_CODE);
                    }
                } else {
                    if (isProgressExist(DAOQueryMode.QUERY_BY_PROJECT_ID, messageBody)) {
                        progressRepository.deleteAllByProjectId(messageBody);
                    }
                }
                break;
            default:
                System.out.println(message.toString());
        }
    }

    @Override
    public boolean checkProgressExistence(String progressId) {
        return isProgressExist(DAOQueryMode.QUERY_BY_ID, progressId);
    }

    @Override
    public ProgressDTO createProgress(ProgressDTO progressDTO) {
        if (userClient.checkUserExistence(progressDTO.getOwnerId())) {
            if (projectClient.checkProjectExistence(progressDTO.getProjectId())) {
                Progress progress = convertFromProgressDTOToProgress(progressDTO);
                progress.setOrder(progressRepository.countByProjectId(progress.getProjectId()));
                progressRepository.save(progress);
                return convertFromProgressToProgressDTO(progress);
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST,
                        String.format("Project %s does not exist", progressDTO.getProjectId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", progressDTO.getOwnerId())
            );
        }
    }

    @Override
    public void deleteProgressById(ProgressDTO progressDTO) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_ID, progressDTO.getProgressId())) {
            Progress progress = progressRepository.findById(progressDTO.getProgressId());
            if (progress.getId().equals(progressDTO.getProgressId())) {
                progressRepository.deleteById(progressDTO.getProgressId());
                resetIndexForProgressListOfProject(progress);

                rabbitTemplate.convertAndSend(
                        PROGRESS_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.PROGRESS_DELETED,
                                progressDTO.getProgressId()
                        )
                );
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("Progress %s does not exist", progressDTO.getProgressId())
            );
        }
    }

    @Override
    public ProgressDTO updateProgressName(ProgressDTO progressDTO) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_ID, progressDTO.getProgressId())) {
            Progress progress = progressRepository.findById(progressDTO.getProgressId());
            if (progress.getId().equals(progressDTO.getProgressId())) {
                progress.setName(progressDTO.getName());
                progressRepository.save(progress);
                return convertFromProgressToProgressDTO(progress);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("Progress %s does not exist", progressDTO.getProgressId())
            );
        }
    }

    @Override
    public ProgressDTO changeProgressOrder(ProgressDTO progressDTO) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_ID, progressDTO.getProgressId())) {
            Progress progress = progressRepository.findById(progressDTO.getProgressId());
            if (progress.getId().equals(progressDTO.getProgressId())) {
                progressDTO = adjustIndex(progressDTO);
                if (progress.getOrder() < progressDTO.getOrder()) {
                    ArrayList<Progress> progressList = progressRepository.findAllByProjectIdOrderByIndexAsc(progress.getProjectId());
                    for (Progress iter : progressList) {
                        if (iter.getOrder() > progress.getOrder() && iter.getOrder() <= progressDTO.getOrder()) {
                            iter.setOrder(iter.getOrder() - 1);
                            progressRepository.save(iter);
                        } else if (iter.getOrder() > progressDTO.getOrder()) {
                            break;
                        }
                    }
                } else {
                    ArrayList<Progress> progressList = progressRepository.findAllByProjectIdOrderByIndexAsc(progress.getProjectId());
                    for (Progress iter : progressList) {
                        if (iter.getOrder() >= progressDTO.getOrder() && iter.getOrder() < progress.getOrder()) {
                            iter.setOrder(iter.getOrder() + 1);
                            progressRepository.save(iter);
                        } else if (iter.getOrder() > progress.getOrder()) {
                            break;
                        }
                    }
                }
                progress.setOrder(progressDTO.getOrder());
                progressRepository.save(progress);
                resetIndexForProgressListOfProject(progress);
                progress = progressRepository.findById(progress.getId());
                return convertFromProgressToProgressDTO(progress);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("Progress %s does not exist", progressDTO.getProgressId())
            );
        }
    }

    @Override
    public ProgressDTO getProgressById(String id) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromProgressToProgressDTO(progressRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("Progress %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<ProgressDTO> getProgressListByOwnerId(String ownerId) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            return convertFromProgressListToProgressDTOs(progressRepository.findAllByOwnerId(ownerId));
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("User %s does not have any progress", ownerId)
            );
        }
    }

    @Override
    public ArrayList<ProgressDTO> getProgressListByProjectId(String projectId) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
            return convertFromProgressListToProgressDTOs(progressRepository.findAllByProjectId(projectId));
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("Project %s does not have any progress", projectId)
            );
        }
    }

    @Override
    public ArrayList<ProgressDTO> getProgressListByOwnerIdAndProjectId(String ownerId, String projectId) {
        if (isProgressExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            if (isProgressExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                return convertFromProgressListToProgressDTOs(progressRepository.findAllByOwnerIdAndProjectId(ownerId, projectId));
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST,
                        String.format("Project %s does not have any progress assigned to user %s", projectId, ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    ProgressErrorCode.PROGRESS_NOT_EXIST,
                    String.format("User %s does not have any progress", ownerId)
            );
        }
    }

    @Override
    public ArrayList<ProgressDTO> adminGetAllProgress(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (progressRepository.count() > 0) {
                return convertFromProgressListToProgressDTOs(progressRepository.findAll());
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteProgressById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isProgressExist(DAOQueryMode.QUERY_BY_ID, id)) {
                Progress progress = progressRepository.findById(id);
                progressRepository.deleteById(id);
                resetIndexForProgressListOfProject(progress);

                rabbitTemplate.convertAndSend(
                        PROGRESS_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.PROGRESS_DELETED,
                                id
                        )
                );
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST,
                        String.format("Progress %s does not exist", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteProgressByOwnerId(String ownerId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isProgressExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                ArrayList<Progress> progressList = progressRepository.findAllByOwnerId(ownerId);
                progressRepository.deleteAllByOwnerId(ownerId);

                for (Progress progress : progressList) {
                    resetIndexForProgressListOfProject(progress);

                    rabbitTemplate.convertAndSend(
                            PROGRESS_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.PROGRESS_DELETED,
                                    progress.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST,
                        String.format("User %s does not have any progress", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteProgressByProjectId(String projectId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isProgressExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                ArrayList<Progress> progressList = progressRepository.findAllByProjectId(projectId);
                progressRepository.deleteAllByProjectId(projectId);

                for (Progress progress : progressList) {
                    resetIndexForProgressListOfProject(progress);

                    rabbitTemplate.convertAndSend(
                            PROGRESS_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.PROGRESS_DELETED,
                                    progress.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST,
                        String.format("Project %s does not have any progress", projectId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteProgressByOwnerIdAndProjectId(String ownerId, String projectId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isProgressExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                if (isProgressExist(DAOQueryMode.QUERY_BY_PROJECT_ID, projectId)) {
                    ArrayList<Progress> progressList = progressRepository.findAllByOwnerIdAndProjectId(ownerId, projectId);
                    progressRepository.deleteAllByOwnerIdAndProjectId(ownerId, projectId);

                    for (Progress progress : progressList) {
                        resetIndexForProgressListOfProject(progress);

                        rabbitTemplate.convertAndSend(
                                PROGRESS_FAN_OUT_EXCHANGE,
                                "",
                                RabbitMQMessageCreator.newInstance(
                                        MessageType.PROGRESS_DELETED,
                                        progress.getId()
                                )
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            ProgressErrorCode.PROGRESS_NOT_EXIST,
                            String.format("Project %s does not have any progress assigned to user %s", projectId, ownerId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST,
                        String.format("User %s does not have any progress", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllProgress(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (progressRepository.count() > 0) {
                progressRepository.deleteAll();

                rabbitTemplate.convertAndSend(
                        PROGRESS_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.PROGRESS_DELETED,
                                ADMIN_DELETE_ALL
                        )
                );
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}

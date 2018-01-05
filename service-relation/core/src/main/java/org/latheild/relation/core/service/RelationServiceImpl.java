package org.latheild.relation.core.service;

import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.api.CommonIdentityType;
import org.latheild.common.constant.RelationType;
import org.latheild.common.domain.Message;
import org.latheild.file.api.constant.FileErrorCode;
import org.latheild.project.api.constant.ProjectErrorCode;
import org.latheild.relation.api.constant.RelationErrorCode;
import org.latheild.relation.api.dto.RelationDTO;
import org.latheild.relation.core.client.*;
import org.latheild.relation.core.dao.FileTaskRelationRepository;
import org.latheild.relation.core.dao.UserProjectRelationRepository;
import org.latheild.relation.core.dao.UserScheduleRelationRepository;
import org.latheild.relation.core.dao.UserTaskRelationRepository;
import org.latheild.relation.core.utils.RelationCreator;
import org.latheild.relation.core.utils.RelationToDTOConverter;
import org.latheild.schedule.api.constant.ScheduleErrorCode;
import org.latheild.task.api.constant.TaskErrorCode;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQQueue.RELATION_QUEUE;

@Service
@RabbitListener(queues = RELATION_QUEUE)
public class RelationServiceImpl implements RelationService {
    @Autowired
    UserClient userClient;

    @Autowired
    ProjectClient projectClient;

    @Autowired
    TaskClient taskClient;

    @Autowired
    ScheduleClient scheduleClient;

    @Autowired
    FileClient fileClient;

    @Autowired
    UserProjectRelationRepository userProjectRelationRepository;

    @Autowired
    UserTaskRelationRepository userTaskRelationRepository;

    @Autowired
    UserScheduleRelationRepository userScheduleRelationRepository;

    @Autowired
    FileTaskRelationRepository fileTaskRelationRepository;

    @RabbitHandler
    public void eventHandler(Message message) {
        String messageBody;
        switch (message.getMessageType()) {
            case USER_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    userProjectRelationRepository.deleteAll();
                    userScheduleRelationRepository.deleteAll();
                    userTaskRelationRepository.deleteAll();
                } else {
                    userTaskRelationRepository.deleteAllByUserId(messageBody);
                    userScheduleRelationRepository.deleteAllByUserId(messageBody);
                    userProjectRelationRepository.deleteAllByUserId(messageBody);
                }
                break;
            case PROJECT_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    userProjectRelationRepository.deleteAll();
                } else {
                    userProjectRelationRepository.deleteAllByProjectId(messageBody);
                }
                break;
            case SCHEDULE_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    userScheduleRelationRepository.deleteAll();
                } else {
                    userScheduleRelationRepository.deleteAllByScheduleId(messageBody);
                }
                break;
            case TASK_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    userTaskRelationRepository.deleteAll();
                    fileTaskRelationRepository.deleteAll();
                } else {
                    userTaskRelationRepository.deleteAllByTaskId(messageBody);
                    fileTaskRelationRepository.deleteAllByTaskId(messageBody);
                }
                break;
            case FILE_DELETED:
                messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    fileTaskRelationRepository.deleteAll();
                } else {
                    fileTaskRelationRepository.deleteAllByFileId(messageBody);
                }
                break;
            default:
                System.out.println(message.toString());
        }
    }

    @Override
    public boolean checkProjectMemberExistence(String userId, String projectId) {
        return (userProjectRelationRepository.countByUserIdAndProjectId(userId, projectId) > 0);
    }

    @Override
    public boolean checkScheduleParticipantExistence(String userId, String scheduleId) {
        return (userScheduleRelationRepository.countByUserIdAndScheduleId(userId, scheduleId) > 0);
    }

    @Override
    public boolean checkTaskParticipantExistence(String userId, String taskId) {
        return (userTaskRelationRepository.countByUserIdAndTaskId(userId, taskId) > 0);
    }

    @Override
    public boolean checkTaskAttachmentExistence(String fileId, String taskId) {
        return (fileTaskRelationRepository.countByFileIdAndTaskId(fileId, taskId) > 0);
    }

    @Override
    public void addProjectMember(String userId, String projectId, CommonIdentityType identityType) {
        if (userClient.checkUserExistence(userId)) {
            if (projectClient.checkProjectExistence(projectId)) {
                if (!checkProjectMemberExistence(userId, projectId)) {
                    userProjectRelationRepository.save(
                            RelationCreator.setUserProjectRelation(
                                    userId, projectId, identityType
                            )
                    );
                } else {
                    throw new AppBusinessException(
                            RelationErrorCode.RELATION_EXIST,
                            String.format("User %s is already a member of project %s", userId, projectId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        ProjectErrorCode.PROJECT_NOT_EXIST,
                        String.format("Project %s does not exist", projectId)
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", userId)
            );
        }
    }

    @Override
    public void addScheduleParticipant(String userId, String scheduleId) {
        if (userClient.checkUserExistence(userId)) {
            if (scheduleClient.checkScheduleExistence(scheduleId)) {
                if (!checkScheduleParticipantExistence(userId, scheduleId)) {
                    userScheduleRelationRepository.save(
                            RelationCreator.setUserScheduleRelation(
                                    userId, scheduleId
                            )
                    );
                } else {
                    throw new AppBusinessException(
                            RelationErrorCode.RELATION_EXIST,
                            String.format("User %s is already a participant of schedule %s", userId, scheduleId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        ScheduleErrorCode.SCHEDULE_NOT_EXIST,
                        String.format("Schedule %s does not exist", scheduleId)
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", userId)
            );
        }
    }

    @Override
    public void addTaskParticipant(String userId, String taskId) {
        if (userClient.checkUserExistence(userId)) {
            if (taskClient.checkTaskExistence(taskId)) {
                if (!checkTaskParticipantExistence(userId, taskId)) {
                    userTaskRelationRepository.save(
                            RelationCreator.setUserTaskRelation(
                                    userId, taskId
                            )
                    );
                } else {
                    throw new AppBusinessException(
                            RelationErrorCode.RELATION_EXIST,
                            String.format("User %s is already a participant of task %s", userId, taskId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Task %s does not exist", taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", userId)
            );
        }
    }

    @Override
    public void addTaskAttachment(String fileId, String taskId) {
        if (fileClient.checkFileExistence(fileId)) {
            if (taskClient.checkTaskExistence(taskId)) {
                if (!checkTaskAttachmentExistence(fileId, taskId)) {
                    fileTaskRelationRepository.save(
                            RelationCreator.setFileTaskRelation(
                                    fileId, taskId
                            )
                    );
                } else {
                    throw new AppBusinessException(
                            RelationErrorCode.RELATION_EXIST,
                            String.format("File %s is already attached to task %s", fileId, taskId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Task %s does not exist", taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    FileErrorCode.FILE_NOT_EXIST,
                    String.format("File %s does not exist", fileId)
            );
        }
    }

    @Override
    public void deleteProjectMember(String userId, String projectId) {
        if (checkProjectMemberExistence(userId, projectId)) {
            userProjectRelationRepository.deleteByUserIdAndProjectId(userId, projectId);
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s is not a member of project %s", userId, projectId)
            );
        }
    }

    @Override
    public void deleteScheduleParticipant(String userId, String scheduleId) {
        if (checkScheduleParticipantExistence(userId, scheduleId)) {
            userScheduleRelationRepository.deleteByUserIdAndScheduleId(userId, scheduleId);
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s is not a participant of schedule %s", userId, scheduleId)
            );
        }
    }

    @Override
    public void deleteTaskParticipant(String userId, String taskId) {
        if (checkTaskParticipantExistence(userId, taskId)) {
            userTaskRelationRepository.deleteByUserIdAndTaskId(userId, taskId);
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s is not a participant of task %s", userId, taskId)
            );
        }
    }

    @Override
    public void deleteTaskAttachment(String fileId, String taskId) {
        if (checkTaskAttachmentExistence(fileId, taskId)) {
            fileTaskRelationRepository.deleteByFileIdAndTaskId(fileId, taskId);
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("File %s is not attached to task %s", fileId, taskId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getProjectMembers(String projectId) {
        if (userProjectRelationRepository.countByProjectId(projectId) > 0) {
            return RelationToDTOConverter.convertUserProjectList(
                    userProjectRelationRepository.findAllByProjectId(projectId),
                    RelationType.PROJECT_AND_USER
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("Project %s has not established any relation with users", projectId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getScheduleParticipants(String scheduleId) {
        if (userScheduleRelationRepository.countByScheduleId(scheduleId) > 0) {
            return RelationToDTOConverter.convertUserScheduleList(
                    userScheduleRelationRepository.findAllByScheduleId(scheduleId),
                    RelationType.SCHEDULE_AND_USER
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("Schedule %s has not established any relation with users", scheduleId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getTaskParticipants(String taskId) {
        if (userTaskRelationRepository.countByTaskId(taskId) > 0) {
            return RelationToDTOConverter.convertUserTaskList(
                    userTaskRelationRepository.findAllByTaskId(taskId),
                    RelationType.TASK_AND_USER
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("Task %s has not established any relation with users", taskId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getTaskAttachments(String taskId) {
        if (fileTaskRelationRepository.countByTaskId(taskId) > 0) {
            return RelationToDTOConverter.convertFileTaskList(
                    fileTaskRelationRepository.findAllByTaskId(taskId),
                    RelationType.TASK_AND_FILE
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("Task %s has not established any relation with files", taskId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getUserProjects(String userId) {
        if (userProjectRelationRepository.countByUserId(userId) > 0) {
            return RelationToDTOConverter.convertUserProjectList(
                    userProjectRelationRepository.findAllByUserId(userId),
                    RelationType.USER_AND_PROJECT
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s has not established any relation with projects", userId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getUserSchedules(String userId) {
        if (userScheduleRelationRepository.countByUserId(userId) > 0) {
            return RelationToDTOConverter.convertUserScheduleList(
                    userScheduleRelationRepository.findAllByUserId(userId),
                    RelationType.USER_AND_SCHEDULE
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s has not established any relation with schedules", userId)
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getUserTasks(String userId) {
        if (userTaskRelationRepository.countByUserId(userId) > 0) {
            return RelationToDTOConverter.convertUserTaskList(
                    userTaskRelationRepository.findAllByUserId(userId),
                    RelationType.USER_AND_TASK
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s has not established any relation with tasks")
            );
        }
    }

    @Override
    public ArrayList<RelationDTO> getFileTasks(String fileId) {
        if (fileTaskRelationRepository.countByFileId(fileId) > 0) {
            return RelationToDTOConverter.convertFileTaskList(
                    fileTaskRelationRepository.findAllByFileId(fileId),
                    RelationType.FILE_AND_TASK
            );
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("File %s has not established any relation with tasks", fileId)
            );
        }
    }

    @Override
    public CommonIdentityType getMemberIdentityOfProject(String userId, String projectId) {
        if (checkProjectMemberExistence(userId, projectId)) {
            return userProjectRelationRepository.findByUserIdAndProjectId(userId, projectId).getIdentityType();
        } else {
            throw new AppBusinessException(
                    RelationErrorCode.RELATION_NOT_EXIST,
                    String.format("User %s is not a member of project %s", userId, projectId)
            );
        }
    }
}

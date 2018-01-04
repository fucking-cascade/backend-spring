package org.latheild.subtask.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.subtask.api.constant.SubtaskErrorCode;
import org.latheild.subtask.api.dto.SubtaskDTO;
import org.latheild.subtask.client.UserClient;
import org.latheild.subtask.constant.DAOQueryMode;
import org.latheild.subtask.dao.SubtaskRepository;
import org.latheild.subtask.domain.Subtask;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.common.constant.RabbitMQQueue.SUBTASK_QUEUE;

@Service
@RabbitListener(queues = SUBTASK_QUEUE)
public class SubtaskServiceImpl implements SubtaskService {
    @Autowired
    UserClient userClient;

    @Autowired
    SubtaskRepository subtaskRepository;

    private boolean isSubtaskCreated(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (subtaskRepository.countById(target) > 0);
            case QUERY_BY_TASK_ID:
                return (subtaskRepository.countByTaskId(target) > 0);
            case QUERY_BY_USER_ID:
                return (subtaskRepository.countByUserId(target) > 0);
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private SubtaskDTO convertFromSubtaskToSubtaskDTO(Subtask subtask) {
        SubtaskDTO subtaskDTO = new SubtaskDTO();
        subtaskDTO.setContent(subtask.getContent());
        subtaskDTO.setTaskId(subtask.getTaskId());
        subtaskDTO.setUserId(subtask.getUserId());
        subtaskDTO.setTaskStatus(subtask.getTaskStatus());
        subtaskDTO.setSubtaskId(subtask.getId());
        return subtaskDTO;
    }

    private ArrayList<SubtaskDTO> convertFromSubtasksToSubtaskDTOs(ArrayList<Subtask> subtasks) {
        ArrayList<SubtaskDTO> subtaskDTOs = new ArrayList<>();
        for (Subtask subtask : subtasks) {
            subtaskDTOs.add(convertFromSubtaskToSubtaskDTO(subtask));
        }
        return subtaskDTOs;
    }

    private Subtask convertFromSubtaskDTOToSubtask(SubtaskDTO subtaskDTO) {
        Subtask subtask = new Subtask();
        subtask.setContent(subtaskDTO.getContent());
        subtask.setTaskStatus(subtaskDTO.getTaskStatus());
        subtask.setUserId(subtaskDTO.getUserId());
        subtask.setTaskId(subtaskDTO.getTaskId());
        return subtask;
    }

    @Override
    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) {
        if (userClient.checkUserExistence(subtaskDTO.getUserId())) {
            /*if (taskClient.checkTaskExistance(subtaskDTO.getTaskId())) {
                Subtask subtask = convertFromSubtaskDTOToSubtask(subtaskDTO);
                subtaskRepository.save(subtask);
                subtaskDTO.setSubtaskId(subtask.getId());
                return subtaskDTO;
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TaskNotExist,
                        String.format("Task %s does not exist", subtaskDTO.getTaskId())
                );
            }*/
            Subtask subtask = convertFromSubtaskDTOToSubtask(subtaskDTO);
            subtaskRepository.save(subtask);
            subtaskDTO.setSubtaskId(subtask.getId());
            return subtaskDTO;
        } else {
            throw new AppBusinessException(
                    UserErrorCode.UserNotExist,
                    String.format("User %s does not exist", subtaskDTO.getUserId())
            );
        }
    }

    @Override
    public void deleteSubtaskById(SubtaskDTO subtaskDTO) {
        if (isSubtaskCreated(DAOQueryMode.QUERY_BY_ID, subtaskDTO.getSubtaskId())) {
            Subtask subtask = subtaskRepository.findById(subtaskDTO.getSubtaskId());
            if (subtaskDTO.getUserId().equals(subtask.getUserId())) {
                subtaskRepository.deleteById(subtaskDTO.getSubtaskId());
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    SubtaskErrorCode.SubtaskNotExist,
                    String.format("Subtask %s does not exist", subtaskDTO.getSubtaskId())
            );
        }
    }

    @Override
    public SubtaskDTO updateSubtaskContent(SubtaskDTO subtaskDTO) {
        if (isSubtaskCreated(DAOQueryMode.QUERY_BY_ID, subtaskDTO.getSubtaskId())) {
            Subtask subtask = subtaskRepository.findById(subtaskDTO.getSubtaskId());
            if (subtaskDTO.getUserId().equals(subtask.getUserId())) {
                subtask.setContent(subtaskDTO.getContent());
                subtaskRepository.save(subtask);
                return subtaskDTO;
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    SubtaskErrorCode.SubtaskNotExist,
                    String.format("Subtask %s does not exist", subtaskDTO.getSubtaskId())
            );
        }
    }

    @Override
    public SubtaskDTO updateSubtaskState(SubtaskDTO subtaskDTO) {
        if (isSubtaskCreated(DAOQueryMode.QUERY_BY_ID, subtaskDTO.getSubtaskId())) {
            Subtask subtask = subtaskRepository.findById(subtaskDTO.getSubtaskId());
            if (subtaskDTO.getUserId().equals(subtask.getUserId())) {
                subtask.setTaskStatus(subtaskDTO.getTaskStatus());
                subtaskRepository.save(subtask);
                return subtaskDTO;
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    SubtaskErrorCode.SubtaskNotExist,
                    String.format("Subtask %s does not exist", subtaskDTO.getSubtaskId())
            );
        }
    }

    @Override
    public SubtaskDTO getSubtaskById(String id) {
        if (isSubtaskCreated(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromSubtaskToSubtaskDTO(subtaskRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    SubtaskErrorCode.SubtaskNotExist,
                    String.format("Subtask %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<SubtaskDTO> getSubtasksByUserId(String userId) {
        if (isSubtaskCreated(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
            return convertFromSubtasksToSubtaskDTOs(subtaskRepository.findAllByUserId(userId));
        } else {
            throw new AppBusinessException(
                    SubtaskErrorCode.SubtaskNotExist,
                    String.format("No subtask has been assigned to user %s", userId)
            );
        }
    }

    @Override
    public ArrayList<SubtaskDTO> getSubtasksByTaskId(String taskId) {
        if (isSubtaskCreated(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
            return convertFromSubtasksToSubtaskDTOs(subtaskRepository.findAllByTaskId(taskId));
        } else {
            throw new AppBusinessException(
                    SubtaskErrorCode.SubtaskNotExist,
                    String.format("No subtask has been assigned to task %s", taskId)
            );
        }
    }

    @Override
    public ArrayList<SubtaskDTO> adminGetAllSubtasks(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (subtaskRepository.count() > 0) {
                return convertFromSubtasksToSubtaskDTOs(subtaskRepository.findAll());
            } else {
                throw new AppBusinessException(
                        SubtaskErrorCode.SubtaskNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteSubtaskById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isSubtaskCreated(DAOQueryMode.QUERY_BY_ID, id)) {
                subtaskRepository.deleteById(id);
            } else {
                throw new AppBusinessException(
                        SubtaskErrorCode.SubtaskNotExist,
                        String.format("Subtask %s was not created", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllSubtasksByUserId(String userId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isSubtaskCreated(DAOQueryMode.QUERY_BY_USER_ID, userId)) {
                subtaskRepository.deleteAllByUserId(userId);
            } else {
                throw new AppBusinessException(
                        SubtaskErrorCode.SubtaskNotExist,
                        String.format("No subtask has been assigned to user %s", userId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllSubtasksByTaskId(String taskId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isSubtaskCreated(DAOQueryMode.QUERY_BY_TASK_ID, taskId)) {
                subtaskRepository.deleteAllByTaskId(taskId);
            } else {
                throw new AppBusinessException(
                        SubtaskErrorCode.SubtaskNotExist,
                        String.format("No subtask has been assigned to task %s", taskId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllSubtasks(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (subtaskRepository.count() > 0) {
                subtaskRepository.deleteAll();
            } else {
                throw new AppBusinessException(
                        SubtaskErrorCode.SubtaskNotExist
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}

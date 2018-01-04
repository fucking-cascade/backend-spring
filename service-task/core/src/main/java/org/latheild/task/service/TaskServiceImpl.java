package org.latheild.task.service;

import org.latheild.apiutils.api.CommonErrorCode;
import org.latheild.apiutils.exception.AppBusinessException;
import org.latheild.common.api.RabbitMQMessageCreator;
import org.latheild.common.constant.MessageType;
import org.latheild.common.domain.Message;
import org.latheild.progress.api.constant.ProgressErrorCode;
import org.latheild.progress.api.dto.ProgressDTO;
import org.latheild.task.api.constant.TaskErrorCode;
import org.latheild.task.api.dto.TaskDTO;
import org.latheild.task.client.ProgressClient;
import org.latheild.task.client.UserClient;
import org.latheild.task.constant.DAOQueryMode;
import org.latheild.task.dao.TaskRepository;
import org.latheild.task.domain.Task;
import org.latheild.task.utils.TutorialTaskCreator;
import org.latheild.user.api.constant.UserErrorCode;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static org.latheild.apiutils.constant.Constants.ADMIN_CODE;
import static org.latheild.apiutils.constant.Constants.ADMIN_DELETE_ALL;
import static org.latheild.common.constant.RabbitMQExchange.TASK_FAN_OUT_EXCHANGE;
import static org.latheild.common.constant.RabbitMQQueue.TASK_QUEUE;

@Service
@RabbitListener(queues = TASK_QUEUE)
public class TaskServiceImpl implements TaskService {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    UserClient userClient;

    @Autowired
    ProgressClient progressClient;

    @Autowired
    TaskRepository taskRepository;

    private boolean isTaskExist(DAOQueryMode mode, String target) {
        switch (mode) {
            case QUERY_BY_ID:
                return (taskRepository.countById(target) > 0);
            case QUERY_BY_OWNER_ID:
                return (taskRepository.countByOwnerId(target) > 0);
            case QUERY_BY_PROGRESS_ID:
                return (taskRepository.countByProgressId(target) > 0);
            default:
                throw new AppBusinessException(
                        CommonErrorCode.INTERNAL_ERROR
                );
        }
    }

    private TaskDTO convertFromTaskToTaskDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setContent(task.getContent());
        taskDTO.setName(task.getName());
        taskDTO.setOwnerId(task.getOwnerId());
        taskDTO.setPriority(task.getPriority());
        taskDTO.setProgressId(task.getProgressId());
        taskDTO.setIndex(task.getIndex());
        taskDTO.setTaskStatus(task.getTaskStatus());
        taskDTO.setTaskId(task.getId());
        return taskDTO;
    }

    private ArrayList<TaskDTO> convertFromTasksToTaskDTOs(ArrayList<Task> tasks) {
        ArrayList<TaskDTO> taskDTOs = new ArrayList<>();
        for (Task task : tasks) {
            taskDTOs.add(convertFromTaskToTaskDTO(task));
        }
        return taskDTOs;
    }

    private Task convertFromTaskDTOToTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setContent(taskDTO.getContent());
        task.setIndex(taskDTO.getIndex());
        task.setName(taskDTO.getName());
        task.setOwnerId(taskDTO.getOwnerId());
        task.setPriority(taskDTO.getPriority());
        task.setTaskStatus(taskDTO.getTaskStatus());
        task.setProgressId(taskDTO.getProgressId());
        return task;
    }

    private ArrayList<Task> convertFromTaskDTOsToTasks(ArrayList<TaskDTO> taskDTOs) {
        ArrayList<Task> tasks = new ArrayList<>();
        for (TaskDTO taskDTO : taskDTOs) {
            tasks.add(convertFromTaskDTOToTask(taskDTO));
        }
        return tasks;
    }

    private TaskDTO adjustIndex(TaskDTO taskDTO) {
        if (taskDTO.getIndex() >= taskRepository.countByProgressId(taskDTO.getProgressId())) {
            taskDTO.setIndex(taskRepository.countByProgressId(taskDTO.getProgressId()) - 1);
        } else if (taskDTO.getIndex() < 0) {
            taskDTO.setIndex(0);
        }
        return taskDTO;
    }

    private void resetIndexForTaskListOfProgress(Task task) {
        ArrayList<Task> tasks = taskRepository.findAllByProgressIdOrderByIndexAsc(task.getProgressId());
        int i = 0;
        for (Task iter : tasks) {
            iter.setIndex(i);
            i++;
            taskRepository.save(iter);
        }
    }

    @RabbitHandler
    public void eventHandler(Message message) {
        switch (message.getMessageType()) {
            case TUTORIAL_PROGRESS_CREATED:
                ProgressDTO progressDTO = (ProgressDTO) message.getMessageBody();

                if (progressDTO.getIndex() == 0) {
                    ArrayList<Task> tasks = convertFromTaskDTOsToTasks(
                            TutorialTaskCreator.setTutorialTasks(progressDTO.getOwnerId(), progressDTO.getProgressId())
                    );
                    for (Task task : tasks) {
                        task.setIndex(taskRepository.countByProgressId(task.getProgressId()));
                        taskRepository.save(task);

                        rabbitTemplate.convertAndSend(
                                TASK_FAN_OUT_EXCHANGE,
                                "",
                                RabbitMQMessageCreator.newInstance(
                                        MessageType.TUTORIAL_TASK_CREATED,
                                        convertFromTaskToTaskDTO(task)
                                )
                        );
                    }
                }
                break;
            case PROGRESS_DELETED:
                String messageBody = (String) message.getMessageBody();
                if (messageBody.equals(ADMIN_DELETE_ALL)) {
                    if (taskRepository.count() > 0) {
                        adminDeleteAllTasks(ADMIN_CODE);
                    }
                } else {
                    if (isTaskExist(DAOQueryMode.QUERY_BY_PROGRESS_ID, messageBody)) {
                        taskRepository.deleteAllByProgressId(messageBody);
                    }
                }
                break;
        }
    }

    @Override
    public boolean checkTaskExistence(String taskId) {
        return isTaskExist(DAOQueryMode.QUERY_BY_ID, taskId);
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        if (userClient.checkUserExistence(taskDTO.getOwnerId())) {
            if (progressClient.checkProgressExistence(taskDTO.getProgressId())) {
                Task task = convertFromTaskDTOToTask(taskDTO);
                task.setIndex(taskRepository.countByProgressId(task.getProgressId()));
                taskRepository.save(task);
                return convertFromTaskToTaskDTO(task);
            } else {
                throw new AppBusinessException(
                        ProgressErrorCode.PROGRESS_NOT_EXIST,
                        String.format("Progress %s does not exist", taskDTO.getProgressId())
                );
            }
        } else {
            throw new AppBusinessException(
                    UserErrorCode.USER_NOT_EXIST,
                    String.format("User %s does not exist", taskDTO.getOwnerId())
            );
        }
    }

    @Override
    public void deleteTaskById(TaskDTO taskDTO) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_ID, taskDTO.getTaskId())) {
            Task task = taskRepository.findById(taskDTO.getTaskId());
            if (task.getOwnerId().equals(taskDTO.getOwnerId())) {
                taskRepository.deleteById(taskDTO.getTaskId());
                resetIndexForTaskListOfProgress(task);

                rabbitTemplate.convertAndSend(
                        TASK_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.TASK_DELETED,
                                taskDTO.getTaskId()
                        )
                );
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Task %s does not exist", taskDTO.getTaskId())
            );
        }
    }

    @Override
    public TaskDTO updateTaskInfo(TaskDTO taskDTO) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_ID, taskDTO.getTaskId())) {
            Task task = taskRepository.findById(taskDTO.getTaskId());
            if (task.getOwnerId().equals(taskDTO.getOwnerId())) {
                task.setPriority(taskDTO.getPriority());
                task.setName(taskDTO.getName());
                task.setContent(taskDTO.getContent());
                taskRepository.save(task);
                return convertFromTaskToTaskDTO(task);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Task %s does not exist", taskDTO.getTaskId())
            );
        }
    }

    @Override
    public TaskDTO updateTaskState(TaskDTO taskDTO) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_ID, taskDTO.getTaskId())) {
            Task task = taskRepository.findById(taskDTO.getTaskId());
            if (task.getOwnerId().equals(taskDTO.getOwnerId())) {
                task.setTaskStatus(taskDTO.getTaskStatus());
                taskRepository.save(task);
                return convertFromTaskToTaskDTO(task);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Task %s does not exist", taskDTO.getTaskId())
            );
        }
    }

    @Override
    public TaskDTO changeTaskOrder(TaskDTO taskDTO) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_ID, taskDTO.getTaskId())) {
            Task task = taskRepository.findById(taskDTO.getTaskId());
            if (task.getOwnerId().equals(taskDTO.getOwnerId())) {
                taskDTO = adjustIndex(taskDTO);
                if (task.getIndex() != taskDTO.getIndex()) {
                    ArrayList<Task> tasks = taskRepository.findAllByProgressIdOrderByIndexAsc(taskDTO.getProgressId());
                    for (Task iter : tasks) {
                        if (task.getIndex() < taskDTO.getIndex()) {
                            if (iter.getIndex() > task.getIndex() && iter.getIndex() <= taskDTO.getIndex()) {
                                iter.setIndex(iter.getIndex() - 1);
                                taskRepository.save(iter);
                            } else if (iter.getIndex() > taskDTO.getIndex()) {
                                break;
                            }
                        } else {
                            if (iter.getIndex() >= taskDTO.getIndex() && iter.getIndex() < task.getIndex()) {
                                iter.setIndex(iter.getIndex() + 1);
                                taskRepository.save(iter);
                            } else if (iter.getIndex() > taskDTO.getIndex()) {
                                break;
                            }
                        }
                    }
                    task.setIndex(taskDTO.getIndex());
                    taskRepository.save(task);
                    resetIndexForTaskListOfProgress(task);
                }
                task = taskRepository.findById(task.getId());
                return convertFromTaskToTaskDTO(task);
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Task %s does not exist", taskDTO.getTaskId())
            );
        }
    }

    @Override
    public TaskDTO changeTaskProgress(TaskDTO taskDTO) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_ID, taskDTO.getTaskId())) {
            Task task = taskRepository.findById(taskDTO.getTaskId());
            if (task.getOwnerId().equals(taskDTO.getOwnerId())) {
                if (progressClient.checkProgressExistence(taskDTO.getProgressId())) {
                    if (!task.getProgressId().equals(taskDTO.getProgressId())) {
                        taskDTO = adjustIndex(taskDTO);
                        ArrayList<Task> tasks = taskRepository.findAllByProgressIdOrderByIndexAsc(task.getProgressId());
                        for (Task iter : tasks) {
                            if (iter.getIndex() > task.getIndex()) {
                                iter.setIndex(iter.getIndex() - 1);
                                taskRepository.save(iter);
                            }
                        }
                        tasks = taskRepository.findAllByProgressIdOrderByIndexAsc(taskDTO.getProgressId());
                        for (Task iter : tasks) {
                            if (iter.getIndex() >= taskDTO.getIndex()) {
                                iter.setIndex(iter.getIndex() + 1);
                                taskRepository.save(iter);
                            }
                        }
                        task.setIndex(taskDTO.getIndex());
                        task.setProgressId(taskDTO.getProgressId());
                        taskRepository.save(task);
                        resetIndexForTaskListOfProgress(task);
                    }
                    task = taskRepository.findById(task.getId());
                    return convertFromTaskToTaskDTO(task);
                } else {
                    throw new AppBusinessException(
                            ProgressErrorCode.PROGRESS_NOT_EXIST,
                            String.format("Progress %s does not exist", taskDTO.getProgressId())
                    );
                }
            } else {
                throw new AppBusinessException(
                        CommonErrorCode.UNAUTHORIZED
                );
            }
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Task %s does not exist", taskDTO.getTaskId())
            );
        }
    }

    @Override
    public TaskDTO getTaskById(String id) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_ID, id)) {
            return convertFromTaskToTaskDTO(taskRepository.findById(id));
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Task %s does not exist", id)
            );
        }
    }

    @Override
    public ArrayList<TaskDTO> getTasksByOwnerId(String ownerId) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            return convertFromTasksToTaskDTOs(taskRepository.findAllByOwnerId(ownerId));
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("User %s does not have any task", ownerId)
            );
        }
    }

    @Override
    public ArrayList<TaskDTO> getTasksByProgressId(String progressId) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_PROGRESS_ID, progressId)) {
            return convertFromTasksToTaskDTOs(taskRepository.findAllByProgressId(progressId));
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("Progress %s does not have any task", progressId)
            );
        }
    }

    @Override
    public ArrayList<TaskDTO> getTasksByOwnerIdAndProgressId(String ownerId, String progressId) {
        if (isTaskExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
            if (isTaskExist(DAOQueryMode.QUERY_BY_PROGRESS_ID, progressId)) {
                return convertFromTasksToTaskDTOs(taskRepository.findAllByOwnerIdAndProgressId(ownerId, progressId));
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Progress %s does not have any task assigned to user %s", progressId, ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    TaskErrorCode.TASK_NOT_EXIST,
                    String.format("User %s does not have any task", ownerId)
            );
        }
    }

    @Override
    public ArrayList<TaskDTO> adminGetAllTasks(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (taskRepository.count() > 0) {
                return convertFromTasksToTaskDTOs(taskRepository.findAll());
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteTaskById(String id, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isTaskExist(DAOQueryMode.QUERY_BY_ID, id)) {
                Task task = taskRepository.findById(id);
                taskRepository.deleteById(id);
                resetIndexForTaskListOfProgress(task);

                rabbitTemplate.convertAndSend(
                        TASK_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.TASK_DELETED,
                                id
                        )
                );
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Task %s does not exist", id)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteTasksByOwnerId(String ownerId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isTaskExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                ArrayList<Task> tasks = taskRepository.findAllByOwnerId(ownerId);
                taskRepository.deleteAllByOwnerId(ownerId);

                for (Task task : tasks) {
                    resetIndexForTaskListOfProgress(task);

                    rabbitTemplate.convertAndSend(
                            TASK_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.TASK_DELETED,
                                    task.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("User %s does not have any task", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteTasksByProgressId(String progressId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isTaskExist(DAOQueryMode.QUERY_BY_PROGRESS_ID, progressId)) {
                ArrayList<Task> tasks = taskRepository.findAllByProgressId(progressId);
                taskRepository.deleteAllByProgressId(progressId);

                for (Task task : tasks) {
                    resetIndexForTaskListOfProgress(task);

                    rabbitTemplate.convertAndSend(
                            TASK_FAN_OUT_EXCHANGE,
                            "",
                            RabbitMQMessageCreator.newInstance(
                                    MessageType.TASK_DELETED,
                                    task.getId()
                            )
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("Progress %s does not have any task", progressId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteTasksByOwnerIdAndProgressId(String ownerId, String progressId, String code) {
        if (code.equals(ADMIN_CODE)) {
            if (isTaskExist(DAOQueryMode.QUERY_BY_OWNER_ID, ownerId)) {
                if (isTaskExist(DAOQueryMode.QUERY_BY_PROGRESS_ID, progressId)) {
                    ArrayList<Task> tasks = taskRepository.findAllByOwnerIdAndProgressId(ownerId, progressId);
                    taskRepository.deleteAllByOwnerIdAndProgressId(ownerId, progressId);

                    for (Task task : tasks) {
                        resetIndexForTaskListOfProgress(task);

                        rabbitTemplate.convertAndSend(
                                TASK_FAN_OUT_EXCHANGE,
                                "",
                                RabbitMQMessageCreator.newInstance(
                                        MessageType.TASK_DELETED,
                                        task.getId()
                                )
                        );
                    }
                } else {
                    throw new AppBusinessException(
                            TaskErrorCode.TASK_NOT_EXIST,
                            String.format("Progress %s does not have any task assigned to user %s", progressId, ownerId)
                    );
                }
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST,
                        String.format("User %s does not have any task", ownerId)
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }

    @Override
    public void adminDeleteAllTasks(String code) {
        if (code.equals(ADMIN_CODE)) {
            if (taskRepository.count() > 0) {
                taskRepository.deleteAll();

                rabbitTemplate.convertAndSend(
                        TASK_FAN_OUT_EXCHANGE,
                        "",
                        RabbitMQMessageCreator.newInstance(
                                MessageType.TASK_DELETED,
                                ADMIN_DELETE_ALL
                        )
                );
            } else {
                throw new AppBusinessException(
                        TaskErrorCode.TASK_NOT_EXIST
                );
            }
        } else {
            throw new AppBusinessException(
                    CommonErrorCode.UNAUTHORIZED
            );
        }
    }
}

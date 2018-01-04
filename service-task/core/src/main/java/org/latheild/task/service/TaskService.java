package org.latheild.task.service;

import org.latheild.task.api.dto.TaskDTO;

import java.util.ArrayList;

public interface TaskService {
    boolean checkTaskExistence(String taskId);

    TaskDTO createTask(TaskDTO taskDTO);

    void deleteTaskById(TaskDTO taskDTO);

    TaskDTO updateTaskInfo(TaskDTO taskDTO);

    TaskDTO updateTaskState(TaskDTO taskDTO);

    TaskDTO changeTaskOrder(TaskDTO taskDTO);

    TaskDTO changeTaskProgress(TaskDTO taskDTO);

    TaskDTO getTaskById(String id);

    ArrayList<TaskDTO> getTasksByOwnerId(String ownerId);

    ArrayList<TaskDTO> getTasksByProgressId(String progressId);

    ArrayList<TaskDTO> getTasksByOwnerIdAndProgressId(String ownerId, String progressId);

    ArrayList<TaskDTO> adminGetAllTasks(String code);

    void adminDeleteTaskById(String id, String code);

    void adminDeleteTasksByOwnerId(String ownerId, String code);

    void adminDeleteTasksByProgressId(String progressId, String code);

    void adminDeleteTasksByOwnerIdAndProgressId(String ownerId, String progressId, String code);

    void adminDeleteAllTasks(String code);
}

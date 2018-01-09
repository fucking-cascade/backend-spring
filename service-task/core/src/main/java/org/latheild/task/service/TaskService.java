package org.latheild.task.service;

import org.latheild.task.api.dto.TaskAttachmentOperationDTO;
import org.latheild.task.api.dto.TaskDTO;
import org.latheild.task.api.dto.TaskParticipantOperationDTO;

import java.util.ArrayList;

public interface TaskService {
    String getProjectId(String taskId);

    boolean checkTaskExistence(String taskId);

    TaskDTO createTask(TaskDTO taskDTO);

    void deleteTaskById(TaskDTO taskDTO);

    TaskDTO updateTaskInfo(TaskDTO taskDTO);

    TaskDTO updateTaskState(TaskDTO taskDTO);

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

    void addTaskParticipant(TaskParticipantOperationDTO taskParticipantOperationDTO);

    void removeTaskParticipant(TaskParticipantOperationDTO taskParticipantOperationDTO);

    ArrayList<TaskDTO> getAllTasksByUserId(String userId);

    void addTaskAttachment(TaskAttachmentOperationDTO taskAttachmentOperationDTO);

    void removeTaskAttachment(TaskAttachmentOperationDTO taskAttachmentOperationDTO);

    ArrayList<TaskDTO> getAllTasksByFileId(String fileId);
}

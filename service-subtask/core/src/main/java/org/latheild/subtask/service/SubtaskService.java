package org.latheild.subtask.service;

import org.latheild.subtask.api.dto.SubtaskDTO;

import java.util.ArrayList;

public interface SubtaskService {
    SubtaskDTO createSubtask(SubtaskDTO subtaskDTO);

    void deleteSubtaskById(SubtaskDTO subtaskDTO);

    SubtaskDTO updateSubtaskContent(SubtaskDTO subtaskDTO);

    SubtaskDTO updateSubtaskState(SubtaskDTO subtaskDTO);

    SubtaskDTO getSubtaskById(String id);

    ArrayList<SubtaskDTO> getSubtasksByUserId(String userId);

    ArrayList<SubtaskDTO> getSubtasksByTaskId(String taskId);

    ArrayList<SubtaskDTO> getSubtasksByUserIdAndTaskId(String userId, String taskId);

    ArrayList<SubtaskDTO> adminGetAllSubtasks(String code);

    void adminDeleteSubtaskById(String id, String code);

    void adminDeleteAllSubtasksByUserId(String userId, String code);

    void adminDeleteAllSubtasksByTaskId(String taskId, String code);

    void adminDeleteAllSubtasksByUserIdAndTaskId(String userId, String taskId, String code);

    void adminDeleteAllSubtasks(String code);
}

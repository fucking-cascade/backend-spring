package org.latheild.subtask.service;

import org.latheild.subtask.api.dto.SubtaskDTO;

import java.util.ArrayList;

public interface SubtaskService {
    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO);

    public void deleteSubtaskById(SubtaskDTO subtaskDTO);

    public SubtaskDTO updateSubtaskContent(SubtaskDTO subtaskDTO);

    public SubtaskDTO updateSubtaskState(SubtaskDTO subtaskDTO);

    public SubtaskDTO getSubtaskById(String id);

    public ArrayList<SubtaskDTO> getSubtasksByUserId(String userId);

    public ArrayList<SubtaskDTO> getSubtasksByTaskId(String taskId);

    public ArrayList<SubtaskDTO> adminGetAllSubtasks();

    public void adminDeleteSubtaskById(String id, String code);

    public void adminDeleteAllSubtasksByUserId(String userId, String code);

    public void adminDeleteAllSubtasksByTaskId(String taskId, String code);

    public void adminDeleteAllSubtasks(String code);
}

package taskService.service;

import taskService.domain.Subtask;

public interface SubtaskService {
    public int generateSubtaskId();

    public void createSubtask(Subtask subtask);

    public void deleteAllSubtasks();
}

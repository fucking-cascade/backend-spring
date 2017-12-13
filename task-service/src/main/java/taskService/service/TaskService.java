package taskService.service;

import taskService.domain.Task;

public interface TaskService {
    public int generateTaskId();

    public Task getTaskById(int id);

    public void createTask(Task task);

    public void addUserToTask(int id, int userId);

    public void addSubtaskToTask(int id, int subtaskId);

    public Boolean removeUserFromTask(int id, int userId);

    public Boolean removeSubtaskFromTask(int id, int subtaskId);

    public Boolean deleteTask(int id, int userId);

    public Boolean updateTask(Task task);
}

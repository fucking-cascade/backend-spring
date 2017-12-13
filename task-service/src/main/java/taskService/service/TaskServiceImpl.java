package taskService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskService.domain.Constants;
import taskService.domain.Task;
import taskService.repository.SubtaskRepository;
import taskService.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Override
    public int generateTaskId() {
        int newId = Constants.RESERVED_NUM_OF_IDS + (int)taskRepository.count();
        return newId;
    }

    @Override
    public Task getTaskById(int id) {
        return taskRepository.findById(id);
    }

    @Override
    public void createTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    public void addUserToTask(int id, int userId) {
        Task task = taskRepository.findById(id);
        if (!task.getUsers().contains(userId)) {
            task.getUsers().add(userId);
            taskRepository.save(task);
        }
    }

    @Override
    public void addSubtaskToTask(int id, int subtaskId) {
        Task task = taskRepository.findById(id);
        if (!task.getSubtasks().contains(subtaskId)) {
            task.getSubtasks().add(subtaskId);
            taskRepository.save(task);
        }
    }

    @Override
    public Boolean removeUserFromTask(int id, int userId) {
        Task task = taskRepository.findById(id);
        if (task.getUsers().contains(userId)) {
            task.removeUser(userId);
            return true;
        }
        return false;
    }

    @Override
    public Boolean removeSubtaskFromTask(int id, int subtaskId) {
        Task task = taskRepository.findById(id);
        if (task.getSubtasks().contains(subtaskId)) {
            task.removeSubtask(subtaskId);
            return true;
        }
        return false;
    }

    @Override
    public Boolean deleteTask(int id, int userId) {
        Task task = taskRepository.findById(id);
        if (task.getOwnerId() == userId) {
            taskRepository.delete(task);
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateTask(Task task) {
        Task oldTask = taskRepository.findById(task.getId());
        if (oldTask == null) {
            return false;
        } else {
            taskRepository.save(task);
            return true;
        }
    }
}

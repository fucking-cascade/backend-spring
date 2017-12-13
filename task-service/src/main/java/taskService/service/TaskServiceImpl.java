package taskService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import taskService.domain.*;
import taskService.repository.TaskRepository;

import java.util.Date;

@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubtaskService subtaskService;

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

    @Override
    public void deleteAllTasks() {
        taskRepository.deleteAll();
        subtaskService.deleteAllSubtasks();
    }

    @Override
    public int createTutorial(int ownerId) {
        int id = generateTaskId();
        Task task = new Task(
                id,
                "Tutorial",
                "This is a tutorial task for newcomers to get used to the system.",
                ownerId,
                TaskState.INPROCESS,
                Priority.HIGH,
                new Date(),
                null,
                null
        );
        task.getUsers().add(ownerId);
        int subtaskId = subtaskService.generateSubtaskId();
        Subtask subtask = new Subtask(
                subtaskId,
                "Check out the news!",
                TaskState.INPROCESS,
                ownerId,
                id
        );
        subtaskService.createSubtask(subtask);
        task.getSubtasks().add(subtaskId);
        subtaskId = subtaskService.generateSubtaskId();
        subtask = new Subtask(
                subtaskId,
                "Join a discussion!",
                TaskState.INPROCESS,
                ownerId,
                id
        );
        task.getSubtasks().add(subtaskId);
        subtaskService.createSubtask(subtask);

        taskRepository.save(task);
        return id;
    }
}

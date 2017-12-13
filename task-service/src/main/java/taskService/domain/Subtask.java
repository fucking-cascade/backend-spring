package taskService.domain;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Subtask {
    @Id
    @NotNull
    private final int id;

    @NotBlank
    private String content;

    private TaskState taskState;

    private int ownerId;

    private int taskId;

    public Subtask(int id, String content, TaskState taskState, int ownerId, int taskId) {
        this.id = id;
        this.content = content;
        this.taskState = taskState;
        this.ownerId = ownerId;
        this.taskId = taskId;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public TaskState getTaskState() {
        return taskState;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public int getTaskId() {
        return taskId;
    }
}

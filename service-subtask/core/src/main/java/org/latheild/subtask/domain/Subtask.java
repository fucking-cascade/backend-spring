package org.latheild.subtask.domain;

import org.latheild.common.api.CommonTaskStatus;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Subtask {
    @Id
    @NotNull
    private String id;

    private String userId;

    private String taskId;

    private String content;

    private CommonTaskStatus taskStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CommonTaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(CommonTaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }
}

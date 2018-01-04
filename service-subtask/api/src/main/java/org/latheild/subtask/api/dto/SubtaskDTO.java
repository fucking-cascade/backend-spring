package org.latheild.subtask.api.dto;

import org.latheild.common.api.CommonTaskStatus;

import java.io.Serializable;

public class SubtaskDTO implements Serializable {
    private String subtaskId;

    private String userId;

    private String taskId;

    private String content;

    private CommonTaskStatus taskStatus;

    public String getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(String subtaskId) {
        this.subtaskId = subtaskId;
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

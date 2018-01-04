package org.latheild.task.api.dto;

import org.latheild.common.api.CommonPriority;
import org.latheild.common.api.CommonTaskStatus;

import java.io.Serializable;

public class TaskDTO implements Serializable {
    private String taskId;

    private String ownerId;

    private String progressId;

    private String name;

    private String content;

    private CommonTaskStatus taskStatus;

    private CommonPriority priority;

    private int index;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getProgressId() {
        return progressId;
    }

    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public CommonPriority getPriority() {
        return priority;
    }

    public void setPriority(CommonPriority priority) {
        this.priority = priority;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}

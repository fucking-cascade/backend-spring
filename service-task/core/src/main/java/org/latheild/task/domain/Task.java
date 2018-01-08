package org.latheild.task.domain;

import org.latheild.common.api.CommonPriority;
import org.latheild.common.api.CommonTaskStatus;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

//state -> Bool
//ddl -> String

public class Task {
    @Id
    @NotNull
    private String id;

    private String ownerId;

    private String progressId;

    private String name;

    private String content;

    private CommonTaskStatus taskStatus;

    private CommonPriority priority;

    private int index;

    private String createdAt;

    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}

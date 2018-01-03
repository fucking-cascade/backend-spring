package org.latheild.comment.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Comment {
    @Id
    @NotNull
    private String id;

    private String content;

    private String userId;

    private String taskId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}

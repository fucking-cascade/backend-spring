package org.latheild.relation.core.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class FileTaskRelation {
    @Id
    @NotNull
    private String id;

    private String fileId;

    private String taskId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

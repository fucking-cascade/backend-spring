package org.latheild.progress.domain;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class Progress {
    @Id
    @NotNull
    private String id;

    private String ownerId;

    private String name;

    private String projectId;

    private int index;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
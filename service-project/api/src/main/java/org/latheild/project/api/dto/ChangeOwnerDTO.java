package org.latheild.project.api.dto;

import java.io.Serializable;

public class ChangeOwnerDTO implements Serializable {
    private String projectId;

    private String oldOwnerId;

    private String newOwnerId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getOldOwnerId() {
        return oldOwnerId;
    }

    public void setOldOwnerId(String oldOwnerId) {
        this.oldOwnerId = oldOwnerId;
    }

    public String getNewOwnerId() {
        return newOwnerId;
    }

    public void setNewOwnerId(String newOwnerId) {
        this.newOwnerId = newOwnerId;
    }
}

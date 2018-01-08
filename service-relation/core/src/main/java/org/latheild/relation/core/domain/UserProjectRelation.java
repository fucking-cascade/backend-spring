package org.latheild.relation.core.domain;

import org.latheild.common.api.CommonIdentityType;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserProjectRelation implements Serializable {
    @Id
    @NotNull
    private String id;

    private String userId;

    private String projectId;

    private CommonIdentityType identityType;

    private String createdAt;

    private String updatedAt;

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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public CommonIdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(CommonIdentityType identityType) {
        this.identityType = identityType;
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

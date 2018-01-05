package org.latheild.relation.core.domain;

import org.latheild.common.api.CommonIdentityType;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;

public class UserProjectRelation {
    @Id
    @NotNull
    private String id;

    private String userId;

    private String projectId;

    private CommonIdentityType identityType;

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
}

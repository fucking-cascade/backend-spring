package org.latheild.project.api.dto;

import org.latheild.common.api.CommonIdentityType;

import java.io.Serializable;

public class AddMemberDTO implements Serializable {
    private String projectId;

    private String executorId;

    private String memberId;

    private CommonIdentityType identityType;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getExecutorId() {
        return executorId;
    }

    public void setExecutorId(String executorId) {
        this.executorId = executorId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public CommonIdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(CommonIdentityType identityType) {
        this.identityType = identityType;
    }
}

package org.latheild.relation.api.dto;

import org.latheild.common.api.CommonIdentityType;
import org.latheild.common.constant.RelationType;

import java.io.Serializable;

public class RelationDTO implements Serializable {
    private String stakeHolder;

    private String property;

    private RelationType relationType;

    private CommonIdentityType identityType;

    public String getStakeHolder() {
        return stakeHolder;
    }

    public void setStakeHolder(String stakeHolder) {
        this.stakeHolder = stakeHolder;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public CommonIdentityType getIdentityType() {
        return identityType;
    }

    public void setIdentityType(CommonIdentityType identityType) {
        this.identityType = identityType;
    }
}

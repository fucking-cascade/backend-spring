package org.latheild.common.domain;

import org.latheild.common.constant.RelationType;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Relation implements Serializable {
    @Id
    @NotNull
    private String id;

    private RelationType relationType;

    private String former;

    private String later;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public String getFormer() {
        return former;
    }

    public void setFormer(String former) {
        this.former = former;
    }

    public String getLater() {
        return later;
    }

    public void setLater(String later) {
        this.later = later;
    }
}

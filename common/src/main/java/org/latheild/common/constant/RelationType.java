package org.latheild.common.constant;

public enum  RelationType {
    USER_AND_TASK(0),
    TASK_AND_COMMENT(1);

    private Integer index;

    RelationType(Integer index) {
        this.index = index;
    }
}

package org.latheild.common.constant;

public enum  RelationType {
    USER_AND_PROJECT(0),
    USER_AND_SCHEDULE(1),
    USER_AND_TASK(2),
    FILE_AND_TASK(3),
    PROJECT_AND_USER(4),
    SCHEDULE_AND_USER(5),
    TASK_AND_USER(6),
    TASK_AND_FILE(7);

    private Integer index;

    RelationType(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public String getName() {
        return this.name();
    }
}

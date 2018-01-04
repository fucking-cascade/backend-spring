package org.latheild.common.constant;

public enum MessageType {
    USER_CREATED(1),
    USER_DELETED(2),
    PROJECT_CREATED(3),
    PROJECT_DELETED(4),
    PROGRESS_CREATED(5),
    PROGRESS_DELETED(6),
    TASK_CREATED(7),
    TASK_DELETED(8),
    TUTORIAL_PROJECT_CREATED(9),
    TUTORIAL_PROGRESS_CREATED(10),
    TUTORIAL_TASK_CREATED(11);

    private Integer index;

    MessageType(Integer index) {
        this.index = index;
    }
}

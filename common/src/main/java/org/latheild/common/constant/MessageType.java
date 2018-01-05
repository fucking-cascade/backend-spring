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
    SCHEDULE_CREATED(9),
    SCHEDULE_DELETED(10),
    FILE_CREATED(11),
    FILE_DELETED(12),
    TUTORIAL_PROJECT_CREATED(13),
    TUTORIAL_PROGRESS_CREATED(14),
    TUTORIAL_TASK_CREATED(15);

    private Integer index;

    MessageType(Integer index) {
        this.index = index;
    }
}

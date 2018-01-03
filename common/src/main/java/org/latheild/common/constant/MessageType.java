package org.latheild.common.constant;

public enum MessageType {
    USER_CREATED(1),
    USER_DELETED(2),
    PROJECT_CREATED(3),
    PROJECT_DELETED(4),
    TUTORIAL_PROJECT_CREATED(5);

    private Integer index;

    MessageType(Integer index) {
        this.index = index;
    }
}

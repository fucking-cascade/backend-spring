package org.latheild.common.constant;

public enum MessageType {
    USER_CREATED(1),
    USER_DELETED(2);

    private Integer index;

    MessageType(Integer index) {
        this.index = index;
    }
}

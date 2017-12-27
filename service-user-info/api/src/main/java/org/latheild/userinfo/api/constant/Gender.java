package org.latheild.user.api.constant;

public enum  Gender {
    FEMALE(0),
    MALE(1),
    OTHER(2);

    private Integer index;

    Gender(Integer index) {
        this.index = index;
    }
}

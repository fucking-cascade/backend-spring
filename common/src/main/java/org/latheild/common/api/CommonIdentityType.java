package org.latheild.common.api;

public enum CommonIdentityType implements IdentityType {
    CREATOR(0, "CREATOR"),
    ADMINISTRATOR(1, "ADMINISTRATOR"),
    PARTICIPANT(2, "PARTICIPANT");

    private int index;

    private String description;

    CommonIdentityType(int index, String description) {
        this.index = index;
        this.description = description;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getName() {
        return this.name();
    }
}

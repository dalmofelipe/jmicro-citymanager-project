package com.citymanager.Project.enums;

public enum FolderEnum {

    HEALTH("HEALTH"),
    EDUCATION("EDUCATION"),
    SPORTS("SPORTS"),
    INFRASTRUCTURE("INFRASTRUCTURE");

    private final String value;

    private FolderEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}

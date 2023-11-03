package edu.pollub.accountservice.model;

public enum Role {
    PARENT("USER"), TEACHER("TEACHER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

package com.example.lms.domain;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_TRAINER("ROLE_TRAINER"),
    ROLE_STUDENT("ROLE_STUDENT");

    private final String name;

    Role(String name) {
        this.name = name;
    }

}

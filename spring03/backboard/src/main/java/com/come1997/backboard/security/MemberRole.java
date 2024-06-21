package com.come1997.backboard.security;

import lombok.Getter;

@Getter
public enum MemberRole {
    /*ADMIN("관리자", "ROLE_ADMIN"), USER("사용자", "ROLE");

    MemberRole(String key, String value) {
        this.key = key;
        this.value = value;
    }
    private String key;
    private String value;*/

    ADMIN("ROLE_ADMIN"), USER("ROLE");

    MemberRole(String value) {
        this.value = value;
    }

    private String value;
}

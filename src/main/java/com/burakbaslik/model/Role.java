package com.burakbaslik.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {// Rolleri standart hale getirmek için geliştirilmiş bir interface oluyor granted
    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN"),
    ROLE_MOD("MOD");

    private String value;
    Role(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }

    @Override
    public String getAuthority() {
        return name();
    }

}

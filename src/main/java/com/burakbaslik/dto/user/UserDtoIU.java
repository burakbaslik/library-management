package com.burakbaslik.dto.user;

import com.burakbaslik.model.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoIU {
    private Long id;
    private String password;
    private String username;
    private boolean enabled;
    private Set<Role> authorities;
}

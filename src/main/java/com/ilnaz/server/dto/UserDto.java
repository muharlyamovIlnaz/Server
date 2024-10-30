package com.ilnaz.server.dto;

import com.ilnaz.server.auth.Role;
import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String password;
    private Role role;

}

package com.ilnaz.server.model;

import com.ilnaz.server.auth.Role;
import lombok.Data;

@Data
public class User {
    private long id;
    private String username;
    private String password;
    private Role role;

}

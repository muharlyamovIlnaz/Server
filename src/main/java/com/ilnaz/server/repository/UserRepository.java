package com.ilnaz.server.repository;

import com.ilnaz.server.model.User;

import java.sql.SQLException;

public interface UserRepository {
    void save(User user) throws SQLException;
}

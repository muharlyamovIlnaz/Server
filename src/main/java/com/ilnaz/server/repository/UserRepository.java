package com.ilnaz.server.repository;

import com.ilnaz.server.model.User;

import java.sql.SQLException;

public interface UserRepository {
    void save(User user, byte[] salt) throws SQLException;

    User findByUsername(String username) throws SQLException;

    byte[] getSaltByUserId(long user_id) throws SQLException;
}

package com.ilnaz.server.repository.impl;

import com.ilnaz.server.model.User;
import com.ilnaz.server.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepositoryImpl implements UserRepository {
    private final Connection connection;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void save(User user) throws SQLException {
        try {
            String sql = """ 
                    insert into users(username, password)
                    values( ?, ? )
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
            System.out.println("Запись успешно сохранена");
        } catch (SQLException e) {
            System.out.println("не удалось сохранить в бд. ошибка : " + e);
            throw new SQLException(e);
        }
    }
}

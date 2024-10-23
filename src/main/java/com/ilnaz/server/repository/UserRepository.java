package com.ilnaz.server.repository;

import com.ilnaz.server.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

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

        //todo
    }
}

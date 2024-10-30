package com.ilnaz.server.repository.impl;

import com.ilnaz.server.auth.Role;
import com.ilnaz.server.model.User;
import com.ilnaz.server.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepositoryImpl implements UserRepository {
    private final Connection connection;
    private final Logger log;

    public UserRepositoryImpl(Connection connection) {
        this.connection = connection;
        this.log = LoggerFactory.getLogger(UserRepositoryImpl.class);
    }

    @Override
    public void save(User user, byte[] salt) throws SQLException {

        connection.setAutoCommit(false);

        try {

            String userSql = """ 
                    insert into users(username, password, role)
                    values(?, ?, ?)
                    """;
            PreparedStatement userPreparedStatement = connection.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS);
            userPreparedStatement.setString(1, user.getUsername());
            userPreparedStatement.setString(2, user.getPassword());
            userPreparedStatement.setString(3, String.valueOf(user.getRole()));
            userPreparedStatement.executeUpdate();
            ResultSet generatedKeys = userPreparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long userId = generatedKeys.getLong(1);
                user.setId(userId);
            }


            String saltSql = """ 
                    insert into salt(user_id, salt_value)
                    values(?, ?)
                    """;
            PreparedStatement saltPreparedStatement = connection.prepareStatement(saltSql);
            saltPreparedStatement.setLong(1, user.getId());
            saltPreparedStatement.setBytes(2, salt);
            saltPreparedStatement.executeUpdate();


            connection.commit();
            System.out.println("Запись успешно сохранена");
        } catch (SQLException e) {

            connection.rollback();
            System.out.println("Не удалось сохранить в БД. Ошибка: " + e);
            throw new SQLException(e);
        } finally {

            connection.setAutoCommit(true);
        }
    }


    @Override
    public User findByUsername(String username) throws SQLException {
        log.info("Метод findByUsername. username: {}", username);
        User user = null;
        try {
            String sql = """
                    select * from users 
                    where username = ?
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            log.error("ошибка в findByUsername. error: {}", e.getMessage());
            throw new SQLException(e);
        }
        return user;
    }

    @Override
    public byte[] getSaltByUserId(long user_id) throws SQLException {
        String sql = """
                select salt_value from salt
                where user_id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, user_id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            byte[] salt = resultSet.getBytes("salt_value");
            return salt;
        }else{
            log.error("соль не найден");
            throw new SQLException("соль не найдена");
        }
    }
}

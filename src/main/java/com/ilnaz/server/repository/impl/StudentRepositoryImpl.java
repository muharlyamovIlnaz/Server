package com.ilnaz.server.repository.impl;

import com.ilnaz.server.model.Student;
import com.ilnaz.server.repository.StudentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentRepositoryImpl implements StudentRepository {
    private final Connection connection;

    public StudentRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Student getStudentById(long id) {
        Student student = null;
        try {
            String sql = """
                         select * from student
                         where id = ?;              
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                student = new Student();
                student.setFirstName(resultSet.getString("first_Name"));
                student.setSecondName(resultSet.getString("second_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setGroupId(resultSet.getLong("group_id"));
                student.setId(resultSet.getLong("id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public boolean deleteStudentById(long id) throws SQLException {
        String sql = """
                delete from student
                where id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate() == 1;

    }

    @Override
    public Student addStudent(Student student) {
        try {
            String sql = """
                    INSERT INTO student (first_name, second_name, last_name, group_id)
                    VALUES (?, ?, ?, ?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getSecondName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setLong(4, student.getGroupId());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long studentId = generatedKeys.getLong(1);
                student.setId(studentId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public List<Student> getStudentsByGroupId(long groupId) {
        List<Student> students = new ArrayList<>();
        try {
            String sql = """
                    select * from student
                    where id = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Student student = new Student();
                student.setFirstName(resultSet.getString("first_name"));
                student.setSecondName(resultSet.getString("second_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setGroupId(resultSet.getLong("group_id"));
                student.setId(resultSet.getLong("id"));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

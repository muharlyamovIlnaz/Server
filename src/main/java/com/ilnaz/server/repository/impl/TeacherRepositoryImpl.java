package com.ilnaz.server.repository.impl;

import com.ilnaz.server.model.Teacher;
import com.ilnaz.server.repository.TeacherRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepositoryImpl implements TeacherRepository {
    private final Connection connection;

    public TeacherRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        try {
            String sql = """
                    select * from teacher;      
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getLong("id"));
                teacher.setFirstName(resultSet.getString("first_name"));
                teacher.setSecondName(resultSet.getString("second_name"));
                teacher.setLastName(resultSet.getString("last_name"));
                teacher.setGroupId(resultSet.getLong("group_id"));
                teachers.add(teacher);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    @Override
    public Teacher getTeacherById(long id) {
        Teacher teacher = null;
        try {
            String sql = """
                    Select * from teacher
                    where id = ?;
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacher = new Teacher();
                teacher.setId(resultSet.getLong("id"));
                teacher.setFirstName(resultSet.getString("first_name"));
                teacher.setSecondName(resultSet.getString("second_name"));
                teacher.setLastName(resultSet.getString("last_name"));
                teacher.setGroupId(resultSet.getLong("group_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public boolean deleteTeacher(long id) throws SQLException {
        String sql = """
                delete from teacher
                    where id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate() == 1;
    }

    @Override
    public Teacher addTeacher(Teacher teacher) {
        try {
            String sql = """
                    INSERT INTO teacher (first_name, second_name, last_name, group_id)
                    VALUES (?, ?, ?, ?)
                    """;

            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getSecondName());
            preparedStatement.setString(3, teacher.getLastName());
            preparedStatement.setLong(4, teacher.getGroupId());
            preparedStatement.executeUpdate();


            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long teacherId = generatedKeys.getLong(1);
                teacher.setId(teacherId);
                addTeacherSubjects(teacherId, teacher.getSubjectsId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    private void addTeacherSubjects(long teacherId, List<Long> subjectsId) throws SQLException {

        String sql = "INSERT INTO group_teacher_subject (teacher_id, subject_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (Long subjectId : subjectsId) {
            preparedStatement.setLong(1, teacherId);
            preparedStatement.setLong(2, subjectId);
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }
}

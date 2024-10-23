package com.ilnaz.server.repository;

import com.ilnaz.server.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TeacherRepository {
    private final Connection connection;

    public TeacherRepository(Connection connection) {
        this.connection = connection;
    }

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
                teacher.setHavingGroup(resultSet.getBoolean("having_group"));
                teacher.setGroupId(resultSet.getLong("group_id"));
                teachers.add(teacher);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

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
                teacher.setHavingGroup(resultSet.getBoolean("having_group"));
                teacher.setGroupId(resultSet.getLong("group_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    public boolean deleteTeacher(long id) throws SQLException {
        String sql = """
                delete from teacher
                    where id = ?;
                """;
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, id);
        return preparedStatement.executeUpdate() == 1;
    }

    public Teacher addTeacher(Teacher teacher) {
        try {
            // 1. SQL запрос для добавления учителя
            String sql = """
                    INSERT INTO teacher (first_name, second_name, last_name, having_group, group_id)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            // 2. Подготовка и выполнение запроса
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, teacher.getFirstName());
            preparedStatement.setString(2, teacher.getSecondName());
            preparedStatement.setString(3, teacher.getLastName());
            preparedStatement.setBoolean(4, teacher.isHavingGroup());
            preparedStatement.setLong(5, teacher.getGroupId());
            preparedStatement.executeUpdate();

            // 3. Получаем сгенерированный ID учителя
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long teacherId = generatedKeys.getLong(1);
                teacher.setId(teacherId); // Устанавливаем ID для объекта учителя

                // 4. Теперь добавляем предметы в связующую таблицу
                addTeacherSubjects(teacherId, teacher.getSubjectsId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    private void addTeacherSubjects(long teacherId, List<Long> subjectsId) throws SQLException {
        // SQL для добавления связей между учителем и предметами
        String sql = "INSERT INTO teacher_subjects (teacher_id, subject_id) VALUES (?, ?)";

        // Подготовка батча
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (Long subjectId : subjectsId) {
            preparedStatement.setLong(1, teacherId);
            preparedStatement.setLong(2, subjectId);
            preparedStatement.addBatch(); // Добавляем в батч
        }

        // Выполняем батч
        preparedStatement.executeBatch();
    }
}

package com.ilnaz.server.repository.impl;

import com.ilnaz.server.model.Grade;
import com.ilnaz.server.repository.GradeRepository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class GradeRepositoryImpl implements GradeRepository {
    private final Connection connection;

    public GradeRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Grade giveRating(Grade grade) {
        try {
            String sql = """
                    insert into grade (student_id, subject_id, teacher_id, grade_value, grade_date)
                    values(?,?,?,?,?)
                    """;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, grade.getStudentId());
            preparedStatement.setLong(2, grade.getSubjectId());
            preparedStatement.setLong(3, grade.getTeacherId());
            preparedStatement.setLong(4, grade.getValue());
            preparedStatement.setDate(5, Date.valueOf(LocalDate.now()));

            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                long gradeId = generatedKeys.getLong(1);
                grade.setId(gradeId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grade;
    }
}

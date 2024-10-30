package com.ilnaz.server.repository;

import com.ilnaz.server.model.Grade;

import java.sql.SQLException;

public interface GradeRepository {

    Grade giveRating(Grade grade);
}

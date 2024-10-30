CREATE TABLE teacher_subject (
    teacher_id BIGINT,
    subject_id BIGINT,
    PRIMARY KEY (teacher_id, subject_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(id) ON DELETE CASCADE,
    FOREIGN KEY (subject_id) REFERENCES subject(id) ON DELETE CASCADE
);
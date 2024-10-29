CREATE TABLE IF NOT EXISTS students_group (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);


INSERT INTO students_group (name) VALUES
    ('1A'), ('2A'), ('3A'), ('4A'), ('5A'),
    ('6A'), ('7A'), ('8A'), ('9A'), ('10A'), ('11A');


ALTER TABLE teacher_subject RENAME TO group_teacher_subject;


ALTER TABLE group_teacher_subject ADD COLUMN group_id BIGINT;



ALTER TABLE group_teacher_subject
ADD FOREIGN KEY (group_id) REFERENCES students_group(id) ON DELETE CASCADE;


ALTER TABLE teacher DROP COLUMN having_group;
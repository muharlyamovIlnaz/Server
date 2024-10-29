CREATE TABLE IF NOT EXISTS grade (
  "id" BIGSERIAL PRIMARY KEY,
  "student_id" BIGINT NOT NULL,
  "subject_id" BIGINT NOT NULL,
  "teacher_id" BIGINT NOT NULL,
  "grade_value" INTEGER NOT NULL,
  "grade_date" DATE NOT NULL
);

ALTER TABLE "grade" ADD FOREIGN KEY ("student_id") REFERENCES "student" ("id");

ALTER TABLE "grade" ADD FOREIGN KEY ("subject_id") REFERENCES "subject" ("id");

ALTER TABLE "grade" ADD FOREIGN KEY ("teacher_id") REFERENCES "teacher" ("id");
ALTER TABLE student_course
    DROP CONSTRAINT student_course_course_id_fkey,
    ADD CONSTRAINT student_course_course_id_fkey
        FOREIGN KEY (course_id)
            REFERENCES course (id)
            ON DELETE CASCADE;
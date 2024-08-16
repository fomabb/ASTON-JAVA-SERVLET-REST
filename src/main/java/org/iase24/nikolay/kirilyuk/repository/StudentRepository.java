package org.iase24.nikolay.kirilyuk.repository;

import org.iase24.nikolay.kirilyuk.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT s FROM Student s JOIN s.courses c WHERE c.id = :courseId")
    List<Student> findStudentByCourseId(@Param("courseId") Long courseId);

    @Query("select s from Student s join s.teacher t where t.id=:id ")
    List<Student> findStudentByTeacherId(@Param("id") Long id);
}

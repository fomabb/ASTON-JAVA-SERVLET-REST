package org.iase24.nikolay.kirilyuk.repository;

import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select t from Teacher t where t.course.id=:courseId")
    List<Teacher> findByTeachersByCourseId(@Param("courseId") Long courseId);
}

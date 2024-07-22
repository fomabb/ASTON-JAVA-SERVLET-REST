package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;

import java.util.List;

public interface CourseDao {

    List<CourseDataDTO> getAllCourse();

    Course getCourseById(Long id);

    void addCourse(Course course);

    void deleteCourse(Long id);

    void updateCourse(Course course, Long id);

    void addTeacherInCourse(Long courseId, Integer teacherId);
}

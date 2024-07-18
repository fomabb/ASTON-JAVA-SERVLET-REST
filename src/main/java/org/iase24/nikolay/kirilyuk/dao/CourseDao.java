package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.model.Course;

import java.util.List;

public interface CourseDao {

    List<Course> getAllCourse();

    Course getCourseById(Long id);

    void addCourse(Course course);

    void deleteCourse(Long id);

    void updateCourse(Course course, Long id);

    void addTeacherInCourse(Long courseId, Integer teacherId);
}

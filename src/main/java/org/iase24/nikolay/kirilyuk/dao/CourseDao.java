package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.entity.Course;

import java.util.List;

public interface CourseDao {

    List<Course> getAllCourse();
    void addCourse(Course course);
}

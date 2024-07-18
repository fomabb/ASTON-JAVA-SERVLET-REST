package org.iase24.nikolay.kirilyuk.dao;

public interface StudentsCoursesDao {

    void addStudentInCourse(Integer studentId, Integer courseId);
    void deleteStudentInCourse(Integer studentId, Integer courseId);
}

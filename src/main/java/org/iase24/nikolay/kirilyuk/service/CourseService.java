package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.controller.exception.CourseNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithTeachersDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseService {

    List<CourseDataDTO> getAllCourse();

    CourseDataDTO getCourseById(Long id) throws CourseNotFoundException;

    Course addCourse(Course course);

    void deleteCourseById(Long id);

    void updateCourse(Long id, CourseDataDTO courseDataDTO);

    void addTeacherToCourse(Long courseId, Long teacherId);

    void addStudentToTeacher(Long studentId, Long teacherId);

    CourseWithTeachersDataDTO getCourseByIdWithTeachers(Long courseId);

    CourseWithStudentsDataDTO getAllStudentByCourseId(Long courseId);

    void deleteStudentFromTeacher(Long teacherId, Long studentId);
}

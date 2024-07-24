package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseService {

    List<CourseDataDTO> getAllCourse();

    Optional<CourseDataDTO> getCourseById(Long id);

    void addCourse(Course course);

    void deleteCourseById(Long id);

    void updateCourse(Long id, CourseDataDTO courseDataDTO);

    void addTeacherToCourse(Long courseId, Long teacherId);
}

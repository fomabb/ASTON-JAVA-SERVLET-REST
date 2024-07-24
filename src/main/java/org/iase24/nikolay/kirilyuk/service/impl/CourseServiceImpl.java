package org.iase24.nikolay.kirilyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.repository.CourseRepository;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public List<CourseDataDTO> getAllCourse() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseDataDTO(course.getId(), course.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<CourseDataDTO> getCourseById(Long id) {
        return Optional.ofNullable(courseRepository.findById(id)
                .map(course -> new CourseDataDTO(course.getId(), course.getName()))
                .orElseThrow(() -> new IllegalArgumentException("Course with id %s id not found".formatted(id))));
    }

    @Override
    public void addCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public void updateCourse(Long id, CourseDataDTO courseDataDTO) {
        Optional<Course> courseId = courseRepository.findById(id);
        if (courseId.isPresent()) {
            Course course = courseId.get();
            course.setName(courseDataDTO.getName());
            courseRepository.save(course);
        }
    }

    @Override
    public void addTeacherToCourse(Long courseId, Long teacherId) {
        Optional<Teacher> teacher = teacherRepository.findById(teacherId);
        Optional<Course> course = courseRepository.findById(courseId);

        if (teacher.isPresent() && course.isPresent()) {
            course.get().getTeachers().add(teacher.get());
            courseRepository.save(course.get());
        }
    }
}

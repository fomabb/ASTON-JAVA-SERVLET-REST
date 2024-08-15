package org.iase24.nikolay.kirilyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.controller.exception.CourseNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.*;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.mapper.CourseMapper;
import org.iase24.nikolay.kirilyuk.mapper.StudentMapper;
import org.iase24.nikolay.kirilyuk.repository.CourseRepository;
import org.iase24.nikolay.kirilyuk.repository.StudentRepository;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<CourseDataDTO> getAllCourse() {
        return courseRepository.findAll().stream()
                .map(course -> new CourseDataDTO(course.getId(), course.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDataDTO getCourseById(Long id) throws CourseNotFoundException {
        Optional<CourseDataDTO> courseDataDTO = courseRepository.findById(id)
                .map(courseMapper::map);

        if (courseDataDTO.isEmpty()) {
            throw new CourseNotFoundException("Course with id %s id not found".formatted(id));
        }
        return courseDataDTO.get();
    }

    @Override
    @Transactional
    public Course addCourse(Course course) {
        courseRepository.save(course);
        return course;
    }

    @Override
    public void deleteCourseById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void updateCourse(Long id, CourseDataDTO courseDataDTO) {
        Optional<Course> courseId = courseRepository.findById(id);
        if (courseId.isPresent()) {
            Course course = courseId.get();
            course.setName(courseDataDTO.getName());
            courseRepository.save(course);
        }
    }

    @Override
    @Transactional
    public void addTeacherToCourse(Long courseId, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        Course course = courseRepository.findById(courseId).orElseThrow();
        teacher.setCourse(course);
        teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void addStudentToTeacher(Long studentId, Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow();
        Student student = studentRepository.findById(studentId).orElseThrow();
        student.setTeacher(teacher);
        studentRepository.save(student);
    }

    @Override
    public CourseWithTeachersDataDTO getCourseByIdWithTeachers(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
        List<Teacher> teachers = courseRepository.findByTeachersByCourseId(courseId);

        List<TeacherWithStudentsDataDTO> teacherDataDTOs = teachers.stream()
                .map(teacher -> {
                    List<StudentDataDTO> studentDTOs = teacher.getStudents()
                            .stream()
                            .map(student -> new StudentDataDTO(student.getId(), student.getName(), student.getStatus()))
                            .collect(Collectors.toList());
                    return new TeacherWithStudentsDataDTO(teacher.getId(), teacher.getName(), teacher.getStatus(), studentDTOs);
                }).collect(Collectors.toList());

        return new CourseWithTeachersDataDTO(course.getId(), course.getName(), teacherDataDTOs);
    }

    @Override
    public CourseWithStudentsDataDTO getAllStudentByCourseId(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        List<StudentDataDTO> studentDataDTOs = studentRepository.findStudentByCourseId(courseId).stream()
                .map(studentMapper::map)
                .collect(Collectors.toList());

        return new CourseWithStudentsDataDTO(course.getId(), course.getName(), studentDataDTOs);
    }

    @Override
    @Transactional
    public void deleteStudentFromTeacher(Long teacherId, Long studentId) {
        Teacher course = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        if (course.getStudents().contains(student)) {
            student.setTeacher(null);
            studentRepository.save(student);
        }
    }
}

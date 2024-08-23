package org.iase24.nikolay.kirilyuk.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.mapper.StudentMapper;
import org.iase24.nikolay.kirilyuk.repository.CourseRepository;
import org.iase24.nikolay.kirilyuk.repository.StudentRepository;
import org.iase24.nikolay.kirilyuk.service.StudentService;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseRepository courseRepository;


    @Override
    public List<StudentDataDTO> getAllStudent() {
        return studentRepository.findAll().stream()
                .map(studentMapper::map)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addStudent(List<Student> students) {
        students.forEach(student -> student.setStatus(StatusUser.STUDENT));
        studentRepository.saveAll(students);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {

        Student student = studentRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        student.setTeacher(null);
        student.getCourses().forEach(course -> course.getStudents().remove(student));

        studentRepository.delete(student);
    }

    @Override
    public StudentDataDTO getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Student with id %s id not found".formatted(id)));
    }

    @Transactional
    @Override
    public Course addStudentToCourse(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Course course = courseRepository.findById(courseId).orElse(null);

        if (student != null || course != null) {
            assert student != null;
            student.getCourses().add(course);
            studentRepository.save(student);
        } else {
            throw new IllegalArgumentException("Student with id %s id not found".formatted(studentId));
        }
        return course;
    }
}

package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<StudentDataDTO> getAllStudent();

    void addStudent(List<Student> students);

    void deleteStudent(Long id);

    StudentDataDTO getStudentById(Long id);

    void addStudentToCourse(Long studentId, Long courseId);
}

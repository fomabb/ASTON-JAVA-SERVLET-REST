package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentService {

    List<StudentDataDTO> getAllStudent();

    void addStudent(List<Student> students);

    void deleteStudent(Long id);

    StudentDataDTO getStudentById(Long id);

    Course addStudentToCourse(Long studentId, Long courseId);
}

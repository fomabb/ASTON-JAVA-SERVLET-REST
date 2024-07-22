package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Student;

import java.util.List;

public interface StudentDao {

    List<StudentDataDTO> getAllStudent();

    void addStudent(Student student);

    void deleteStudent(Long id);

    Student getStudentById(Long id);
}

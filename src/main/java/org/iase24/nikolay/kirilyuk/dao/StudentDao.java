package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.model.Student;

import java.util.List;

public interface StudentDao {

    List<Student> getAllStudent();

    void addStudent(Student student);

    void deleteStudent(Long id);

    Student getStudentById(Long id);
}

package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.entity.Teacher;

import java.util.List;

public interface TeacherDao {

    List<Teacher> getAllTeachers();
    void addTeacher(Teacher teacher);
    Teacher getTeacherById(Long id);
}

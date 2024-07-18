package org.iase24.nikolay.kirilyuk.dao;

import org.iase24.nikolay.kirilyuk.model.Teacher;

import java.util.List;

public interface TeacherDao {

    List<Teacher> getAllTeachers();

    void addTeacher(Teacher teacher);

    Teacher getTeacherById(Long id);

    void deleteTeacherById(Long id);
}

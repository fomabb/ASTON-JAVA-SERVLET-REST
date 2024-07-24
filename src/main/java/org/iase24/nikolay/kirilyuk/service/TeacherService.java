package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    List<TeacherDataDTO> getAllTeachers();

    void addTeacher(Teacher teacher);

    Optional<Teacher> getTeacherById(Long id);

    void deleteTeacherById(Long id);
}

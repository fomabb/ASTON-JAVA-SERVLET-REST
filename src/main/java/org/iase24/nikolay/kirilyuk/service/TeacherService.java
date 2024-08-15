package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherService {

    List<TeacherDataDTO> getAllTeachers();

    void addTeacher(List<Teacher> teachers);

    TeacherDataDTO getTeacherById(Long id);

    void deleteTeacherById(Long id);

    TeacherWithStudentsDataDTO getTeacherWithStudents(Long id);
}

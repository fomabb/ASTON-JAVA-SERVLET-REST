package org.iase24.nikolay.kirilyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.mapper.StudentMapper;
import org.iase24.nikolay.kirilyuk.mapper.TeacherMapper;
import org.iase24.nikolay.kirilyuk.repository.StudentRepository;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    @Override
    public List<TeacherDataDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacher -> new TeacherDataDTO(teacher.getId(), teacher.getName(), teacher.getStatus()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addTeacher(List<Teacher> teachers) {
        teachers.forEach(teacher -> teacher.setStatus(StatusUser.TEACHER));
        teacherRepository.saveAllAndFlush(teachers);
    }

    @Override
    public TeacherDataDTO getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Teacher with id %s id not found".formatted(id)));
    }

    @Transactional
    @Override
    public void deleteTeacherById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public TeacherWithStudentsDataDTO getTeacherWithStudents(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Teacher with id %s id not found".formatted(id)));

        List<StudentDataDTO> studentDataDTOs = studentRepository.findStudentByTeacherId(id).stream()
                .map(studentMapper::map)
                .collect(Collectors.toList());

        return new TeacherWithStudentsDataDTO(teacher.getId(), teacher.getName(), teacher.getStatus(), studentDataDTOs);
    }
}

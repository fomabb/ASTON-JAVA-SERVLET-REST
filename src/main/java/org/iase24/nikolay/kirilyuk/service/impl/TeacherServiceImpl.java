package org.iase24.nikolay.kirilyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.mapper.TeacherMapper;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
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

    @Override
    public List<TeacherDataDTO> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacher -> new TeacherDataDTO(teacher.getId(), teacher.getName(), teacher.getStatus()))
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void addTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
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
}

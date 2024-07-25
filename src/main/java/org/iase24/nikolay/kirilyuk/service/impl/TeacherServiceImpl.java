package org.iase24.nikolay.kirilyuk.service.impl;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherServiceImpl implements TeacherService {



    @Override
    public List<TeacherDataDTO> getAllTeachers() {
        return null;
    }

    @Override
    public void addTeacher(Teacher teacher) {

    }

    @Override
    public TeacherDataDTO getTeacherById(Long id) {
        return null;
    }

    @Override
    public void deleteTeacherById(Long id) {

    }
}

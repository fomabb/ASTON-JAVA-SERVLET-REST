package org.iase24.nikolay.kirilyuk.controller;

import jakarta.persistence.EntityNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherController.class)
public class TeacherControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private TeacherService teacherService;

    @Test
    void showAllTeachers_GetAllTeachers() throws Exception {
        TeacherDataDTO teacherDataDTO = createTeacherDTO();

        when(teacherService.getAllTeachers()).thenReturn(Collections.singletonList(teacherDataDTO));

        mockMvc.perform(get("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value(teacherDataDTO.getName()));
    }

    @Test
    void getTeacherById_TeacherNotFound() throws Exception {
        Teacher teacher = createTeacher();

        doThrow(new EntityNotFoundException("Teacher not found"))
                .when(teacherService).getTeacherById(teacher.getId());

        mockMvc.perform(get("/api/teacher/{id}", teacher.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTeacherById_Success() throws Exception {
        TeacherDataDTO teacherDataDTO = createTeacherDTO();

        when(teacherService.getTeacherById(teacherDataDTO.getId())).thenReturn(teacherDataDTO);

        mockMvc.perform(get("/api/teacher/{id}", teacherDataDTO.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value(teacherDataDTO.getName()));

        verify(teacherService, times(1)).getTeacherById(teacherDataDTO.getId());
    }

    @Test
    void createTeacher_Success() throws Exception {
        Teacher teacherFirst = createTeacher();
        Teacher teacherSecond = createTeacher();
        teacherSecond.setId(2L);
        teacherSecond.setName("Vasiliy");
//todo
    }

    private TeacherDataDTO createTeacherDTO() {
        return TeacherDataDTO.builder()
                .id(1L)
                .name("Oleg")
                .status(StatusUser.TEACHER)
                .build();
    }

    private Teacher createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("Oleg")
                .status(StatusUser.TEACHER)
                .course(null)
                .students(new ArrayList<>())
                .build();
        teacher.setId(1L);

        return teacher;
    }
}

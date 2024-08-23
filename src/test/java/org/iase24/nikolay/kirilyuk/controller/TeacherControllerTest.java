package org.iase24.nikolay.kirilyuk.controller;

import jakarta.persistence.EntityNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherWithStudentsDataDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
        Teacher teacherFirst = new Teacher();
        teacherFirst.setId(1L);
        teacherFirst.setName("Valeriy");
        teacherFirst.setStatus(StatusUser.TEACHER);
        Teacher teacherSecond = new Teacher();
        teacherSecond.setId(2L);
        teacherSecond.setName("Olga");
        teacherSecond.setStatus(StatusUser.TEACHER);

        when(teacherService.addTeacher(List.of(teacherFirst, teacherSecond))).thenReturn(List.of(teacherFirst, teacherSecond));

        mockMvc.perform(post("/api/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": 1, \"name\": \"Valeriy\", \"status\": \"TEACHER\"}, " +
                                "{\"id\": 2, \"name\": \"Olga\", \"status\": \"TEACHER\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Valeriy"))
                .andExpect(jsonPath("$[0].status").value("TEACHER"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Olga"));
    }

    @Test
    void removeTeacherWithoutStudentsAndCourse_NotFound_Teacher_ById() throws Exception {
        Teacher teacher = createTeacher();

        doThrow(new EntityNotFoundException("Teacher not found"))
                .when(teacherService).getTeacherById(teacher.getId());

        mockMvc.perform(get("/api/teacher/{id}", teacher.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void removeTeacherWithoutStudentsAndCourse_Success() throws Exception {
        Teacher teacher = createTeacher();

        doNothing().when(teacherService).deleteTeacherById(teacher.getId());

        mockMvc.perform(delete("/api/teacher/{id}", teacher.getId()))
                .andExpect(status().isOk());

        verify(teacherService, times(1)).deleteTeacherById(teacher.getId());
    }

    @Test
    void getTeacherWithStudents_Success() throws Exception {

        StudentDataDTO student = StudentDataDTO.builder()
                .id(2L)
                .status(StatusUser.STUDENT)
                .name("Alex")
                .build();


        TeacherWithStudentsDataDTO teacher = TeacherWithStudentsDataDTO.builder()
                .id(1L)
                .name("Valeriy")
                .status(StatusUser.TEACHER)
                .students(List.of(student))
                .build();

        when(teacherService.getTeacherWithStudents(teacher.getId())).thenReturn(teacher);

        // Выполнение запроса и проверка результата
        mockMvc.perform(get("/api/teacher/{id}/with-students", teacher.getId())) // Изменение на правильный ID учителя
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))  // Проверяем ID учителя
                .andExpect(jsonPath("$.name").value("Valeriy"))  // Проверяем имя учителя
                .andExpect(jsonPath("$.students[0].id").value(2L))  // Проверяем ID студента внутри учителя
                .andExpect(jsonPath("$.students[0].name").value("Alex")); // Проверяем имя студента

        verify(teacherService, times(1)).getTeacherWithStudents(teacher.getId());
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

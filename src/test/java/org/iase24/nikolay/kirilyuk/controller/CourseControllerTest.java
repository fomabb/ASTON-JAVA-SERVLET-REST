package org.iase24.nikolay.kirilyuk.controller;

import jakarta.persistence.EntityNotFoundException;
import org.iase24.nikolay.kirilyuk.controller.exception.CourseNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithTeachersDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    @Test
    void showAllCourses_GetAllCourses() throws Exception {
        CourseDataDTO courseDataDTO = new CourseDataDTO(1L, "Aston");

        when(courseService.getAllCourse()).thenReturn(Collections.singletonList(courseDataDTO));

        mockMvc.perform(get("/api/course"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Aston"));
    }

    @Test
    void getCourseById_CourseNotFound_ReturnsNotFound() throws Exception, CourseNotFoundException {
        when(courseService.getCourseById(1L)).thenThrow(new CourseNotFoundException("Course not found"));

        mockMvc.perform(get("/api/course/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Course not found"));
    }

    @Test
    void createCourse_ValidCourse_ReturnsCreated() throws Exception {
        Course course = new Course();
        course.setId(1L);
        course.setName("Aston");

        when(courseService.addCourse(any(Course.class))).thenReturn(course);

        mockMvc.perform(post("/api/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Aston\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Aston"));
    }

    @Test
    void updateCourse_ValidData_ReturnsNoContent() throws Exception, CourseNotFoundException {
        Long id = 1L;
        CourseDataDTO courseDataDTO = new CourseDataDTO();
        courseDataDTO.setName("Update course name");

        doNothing().when(courseService).updateCourse(id, courseDataDTO);

        mockMvc.perform(put("/api/course/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Course Name\"}"));
    }

    @Test
    public void testAddStudentToTeacher_Success() throws Exception {
        Long courseId = 1L;
        Long teacherId = 2L;

        doNothing().when(courseService).addTeacherToCourse(courseId, teacherId);

        mockMvc.perform(put("/api/course/courseId/{courseId}/teacherId/{teacherId}", courseId, teacherId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(courseService, times(1)).addTeacherToCourse(courseId, teacherId);
    }

    @Test
    public void testAddTeacherTeacherToCourse_Course_Teacher_NotFound() throws Exception {
        Long courseId = 1L;
        Long teacherId = 2L;

        doThrow(new RuntimeException("Course or teacher not found"))
                .when(courseService).addTeacherToCourse(courseId, teacherId);

        mockMvc.perform(put("/api/course/courseId/{courseId}/teacherId/{teacherId}", courseId, teacherId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(courseService, times(1)).addTeacherToCourse(courseId, teacherId);
    }

    @Test
    void addStudentToTeacher_Student_Teacher_NotFound() throws Exception {
        Long StudentId = 1L;
        Long TeacherId = 2L;

        doThrow(new RuntimeException("Student or Teacher not found"))
                .when(courseService).addStudentToTeacher(StudentId, TeacherId);

        mockMvc.perform(put("/api/course/studentId/{studentId}/to/teacherId/{teacherId}", StudentId, TeacherId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(courseService, times(1)).addStudentToTeacher(StudentId, TeacherId);
    }

    @Test
    void courseByIdWithTeachers_Success() throws Exception {
        Long courseId = 1L;
        CourseWithTeachersDataDTO mockCourseWithTeacherDataDTO = new CourseWithTeachersDataDTO();

        when(courseService.getCourseByIdWithTeachers(courseId)).thenReturn(mockCourseWithTeacherDataDTO);

        mockMvc.perform(get("/api/course/{courseId}/teachers", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void courseByIdWithTeachers_NotFound() throws Exception {
        Long courseId = 1L;

        when(courseService.getCourseByIdWithTeachers(courseId)).thenThrow(new RuntimeException("Course not found"));

        mockMvc.perform(get("/api/course/{courseId}/teachers", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllStudentByCourseId_Success() throws Exception {
        Long courseId = 1L;
        CourseWithStudentsDataDTO mockCourseWithStudentDataDTO = new CourseWithStudentsDataDTO();

        when(courseService.getAllStudentByCourseId(courseId)).thenReturn(mockCourseWithStudentDataDTO);

        mockMvc.perform(get("/api/course/{courseId}/with-students", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAllTeacherByCourseId_Notfound() throws Exception {
        Long courseId = 1L;

        when(courseService.getAllStudentByCourseId(courseId)).thenThrow(new RuntimeException("Course not found"));

        mockMvc.perform(get("/api/course/{courseId}/with-students", courseId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteCourseById_Success() throws Exception {
        Long courseId = 1L;

        doNothing().when(courseService).deleteCourseById(courseId);

        mockMvc.perform(delete("/api/course/{id}", courseId))
                .andExpect(status().isOk());

        verify(courseService, times(1)).deleteCourseById(courseId);
    }

    @Test
    void testDeleteCourseById_NotFound() throws Exception {
        Long courseId = 1L;

        doThrow(new EntityNotFoundException("Course not found")).when(courseService).deleteCourseById(courseId);

        mockMvc.perform(delete("/api/course/{id}", courseId))
                .andExpect(status().isNotFound());

        verify(courseService, times(1)).deleteCourseById(courseId);
    }
}

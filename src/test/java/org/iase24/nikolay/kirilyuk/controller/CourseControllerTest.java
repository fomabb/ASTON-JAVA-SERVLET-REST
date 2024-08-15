package org.iase24.nikolay.kirilyuk.controller;

import org.iase24.nikolay.kirilyuk.controller.exception.CourseNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
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
    void updateCourse_ValidData_ReturnsNoContent() throws Exception {
        Long id = 1L;
        CourseDataDTO courseDataDTO = new CourseDataDTO();
        courseDataDTO.setName("Update course name");

        doNothing().when(courseService).updateCourse(id, courseDataDTO);

        mockMvc.perform(put("/api/course/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Updated Course Name\"}"));
    }
}

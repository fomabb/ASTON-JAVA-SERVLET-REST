package org.iase24.nikolay.kirilyuk.controller;

import jakarta.persistence.EntityNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.iase24.nikolay.kirilyuk.service.StudentService;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StudentService studentService;

    @MockBean
    CourseService courseService;

    @Test
    void getAllStudents_ReturnsAllStudents() throws Exception {
        StudentDataDTO studentDataDTO = createStudentDataDTO();
        StudentDataDTO studentDataDTO2 = StudentDataDTO.builder()
                .id(2L)
                .name("Olga")
                .status(StatusUser.STUDENT)
                .build();


        when(studentService.getAllStudent()).thenReturn(List.of(studentDataDTO, studentDataDTO2));

        mockMvc.perform(get("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\":1,\"name\":\"Alex\",\"status\":\"STUDENT\"},{\"id\":2,\"name\":\"Olga\",\"status\":\"STUDENT\"}]"))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Olga"));
    }

    @Test
    void getStudentById_Success() throws Exception {
        StudentDataDTO studentDataDTO = createStudentDataDTO();

        when(studentService.getStudentById(studentDataDTO.getId())).thenReturn(studentDataDTO);

        mockMvc.perform(get("/api/student/{id}", studentDataDTO.getId()))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Alex"));

        verify(studentService, times(1)).getStudentById(studentDataDTO.getId());
    }

    @Test
    void getStudentById_NotFound() throws Exception {
        StudentDataDTO studentDataDTO = createStudentDataDTO();

        doThrow(new EntityNotFoundException("Student not found"))
                .when(studentService).getStudentById(studentDataDTO.getId());

        mockMvc.perform(get("/api/student/{id}", studentDataDTO.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void createStudent_Success() throws Exception {
        Student studentFirst = new Student();
        studentFirst.setId(1L);
        studentFirst.setName("Alex");
        studentFirst.setStatus(StatusUser.STUDENT);

        Student studentSecond = new Student();
        studentSecond.setId(2L);
        studentSecond.setName("Olga");
        studentSecond.setStatus(StatusUser.STUDENT);

        doNothing().when(studentService).addStudent(List.of(studentFirst, studentSecond));

        mockMvc.perform(post("/api/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\": 1, \"name\": \"Alex\", \"status\": \"STUDENT\"}, " +
                                "{\"id\": 2, \"name\": \"Olga\", \"status\": \"STUDENT\"}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Alex"))
                .andExpect(jsonPath("$[0].status").value("STUDENT"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].status").value("STUDENT"))
                .andExpect(jsonPath("$[1].name").value("Olga"));
    }

    @Test
    void removeStudent_Success() throws Exception {
        Student student = createStudent();

        doNothing().when(studentService).deleteStudent(student.getId());

        mockMvc.perform(delete("/api/student/{id}", student.getId()))
                .andExpect(status().isOk());

        verify(studentService, times(1)).deleteStudent(student.getId());
    }

    @Test
    void addStudentToCourse_Success() throws Exception {
        Student student = createStudent();
        Course course = Course.builder()
                .name("Aston")
                .students(new ArrayList<>())
                .teachers(null)
                .build();
        course.setId(3L);

        course.getStudents().add(student);

        when(studentService.addStudentToCourse(student.getId(), course.getId())).thenReturn(course);

        mockMvc.perform(post("/api/student/add/studentId/{studentId}/to/courseId/{courseId}",
                        student.getId(), course.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 3, \"name\": \"Aston\", \"students\": [{\"id\": 2, \"name\": \"Olga\", \"status\": \"STUDENT\"}]}"))
                .andExpect(status().isOk())  // Проверяем, что статус ответа 200 OK
                .andExpect(jsonPath("$.id").value(3L))  // Проверяем, что ID курса равен 3
                .andExpect(jsonPath("$.name").value("Aston"))  // Проверяем имя курса
                .andExpect(jsonPath("$.students[0].id").value(2L))  // Проверяем, что ID студента равен 2
                .andExpect(jsonPath("$.students[0].name").value("Olga"))  // Проверяем имя студента
                .andExpect(jsonPath("$.students[0].status").value("STUDENT"))  // Проверяем статус студента
                .andExpect(jsonPath("$.teachers").doesNotExist());  // Проверяем, что поле 'teachers' отсутствует
    }

    private StudentDataDTO createStudentDataDTO() {
        return StudentDataDTO.builder()
                .id(1L)
                .name("Alex")
                .status(StatusUser.STUDENT)
                .build();
    }

    private Student createStudent() {
        Student student = Student.builder()
                .name("Olga")
                .status(StatusUser.STUDENT)
                .teacher(null)
                .courses(new ArrayList<>())
                .build();
        student.setId(2L);

        return student;
    }
}

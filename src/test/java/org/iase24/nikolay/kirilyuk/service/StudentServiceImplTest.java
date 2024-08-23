package org.iase24.nikolay.kirilyuk.service;

import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.mapper.StudentMapper;
import org.iase24.nikolay.kirilyuk.repository.CourseRepository;
import org.iase24.nikolay.kirilyuk.repository.StudentRepository;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.impl.StudentServiceImpl;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentServiceImpl studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showAllStudent_Success() throws Exception {
        Student studentFirst = new Student();
        studentFirst.setId(1L);
        studentFirst.setName("Oleg");
        studentFirst.setStatus(StatusUser.STUDENT);

        Student studentSecond = new Student();
        studentSecond.setId(2L);
        studentSecond.setName("Alex");
        studentSecond.setStatus(StatusUser.STUDENT);

        StudentDataDTO studentFirstDTO = new StudentDataDTO(1L, "Oleg", StatusUser.STUDENT);
        StudentDataDTO studentSecondDTO = new StudentDataDTO(2L, "Alex", StatusUser.STUDENT);

        when(studentRepository.findAll()).thenReturn(List.of(studentFirst, studentSecond));
        when(studentMapper.map(studentFirst)).thenReturn(studentFirstDTO);
        when(studentMapper.map(studentSecond)).thenReturn(studentSecondDTO);

        List<StudentDataDTO> result = studentService.getAllStudent();

        assertEquals(2, result.size());
        assertEquals(studentFirst.getId(), result.get(0).getId());
        assertEquals(studentSecond.getId(), result.get(1).getId());
        assertEquals("Oleg", result.get(0).getName());
        assertEquals("Alex", result.get(1).getName());
    }

    @Test
    void addStudent_Success() {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Oleg");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Alex");

        List<Student> students = List.of(student1, student2);

        studentService.addStudent(students);

        verify(studentRepository, times(1)).saveAll(students);
        assertEquals(StatusUser.STUDENT, student1.getStatus());
        assertEquals(StatusUser.STUDENT, student2.getStatus());
    }

    @Test
    void deleteStudent_Success() {
        // Arrange
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Professor X");

        Course course = new Course();
        course.setId(1L);
        course.setName("Math");
        course.setStudents(new ArrayList<>());  // Инициализация списка студентов
        course.getStudents().add(new Student()); // Добавление студента в список курса

        Student student = new Student();
        student.setId(1L);
        student.setName("Oleg");
        student.setTeacher(teacher);
        student.setCourses(new ArrayList<>(List.of(course)));

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentService.deleteStudent(1L);

        verify(studentRepository, times(1)).delete(student);
        assertNull(student.getTeacher());
    }

    @Test
    void getStudentById_Success() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Oleg");
        student.setStatus(StatusUser.STUDENT);

        StudentDataDTO studentDataDTO = new StudentDataDTO(1L, "Oleg", StatusUser.STUDENT);

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(studentMapper.map(student)).thenReturn(studentDataDTO);

        StudentDataDTO result = studentService.getStudentById(1L);

        assertEquals(1L, result.getId());
        assertEquals("Oleg", result.getName());
        assertEquals(StatusUser.STUDENT, result.getStatus());
    }

    @Test
    void addStudentToCourse_Success() {
        Student student = new Student();
        student.setId(1L);
        student.setName("Oleg");
        student.setCourses(new ArrayList<>());

        Course course = new Course();
        course.setId(1L);
        course.setName("Math");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        Course result = studentService.addStudentToCourse(1L, 1L);

        verify(studentRepository, times(1)).save(student);
        assertEquals(course, result);
        assertTrue(student.getCourses().contains(course));
    }
}

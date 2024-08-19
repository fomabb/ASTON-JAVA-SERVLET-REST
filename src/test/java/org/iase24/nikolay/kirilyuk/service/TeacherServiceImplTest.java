package org.iase24.nikolay.kirilyuk.service;

import jakarta.persistence.EntityNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.mapper.StudentMapper;
import org.iase24.nikolay.kirilyuk.mapper.TeacherMapper;
import org.iase24.nikolay.kirilyuk.repository.CourseRepository;
import org.iase24.nikolay.kirilyuk.repository.StudentRepository;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.impl.TeacherServiceImpl;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TeacherServiceImplTest {

    @Mock
    TeacherRepository teacherRepository;

    @Mock
    StudentRepository studentRepository;

    @Mock
    CourseRepository courseRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void showAllTeachers_Success() throws Exception {
        Teacher teacherFirst = new Teacher();
        teacherFirst.setId(1L);
        teacherFirst.setName("Oleg");
        teacherFirst.setStatus(StatusUser.TEACHER);

        Teacher teacherSecond = new Teacher();
        teacherSecond.setId(2L);
        teacherSecond.setName("Alisa");
        teacherSecond.setStatus(StatusUser.TEACHER);

        when(teacherRepository.findAll()).thenReturn(List.of(teacherFirst, teacherSecond));

        List<TeacherDataDTO> result = teacherService.getAllTeachers();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals("Alisa", result.get(1).getName());
    }

    @Test
    void showTeacherById_Return_By_Id() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("Alex");
        teacher.setStatus(StatusUser.TEACHER);

        TeacherDataDTO teacherDataDTO = new TeacherDataDTO(teacher.getId(), teacher.getName(), teacher.getStatus());

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(teacherMapper.map(teacher)).thenReturn(teacherDataDTO);

        TeacherDataDTO result = teacherService.getTeacherById(teacher.getId());

        assertEquals(1L, result.getId());
        assertEquals("Alex", result.getName());
        assertEquals(StatusUser.TEACHER, result.getStatus());
    }

    @Test
    void showTeacherById_NotFound() {
        Long id = 1L;

        when(teacherRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.getTeacherById(id));
    }

    @Test
    void createTeacher_Return_Teacher() {
        Teacher teacherFirst = createTeacher();
        Teacher teacherSecond = new Teacher();
        teacherSecond.setId(2L);
        teacherSecond.setName("Alina");
        teacherSecond.setStatus(StatusUser.TEACHER);

        when(teacherRepository.saveAll(List.of(teacherFirst, teacherSecond)))
                .thenReturn(List.of(teacherFirst, teacherSecond));

        List<Teacher> result = teacherService.addTeacher(List.of(teacherFirst, teacherSecond));

        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
        assertEquals(StatusUser.TEACHER, result.get(1).getStatus());
    }

    @Test
    void deleteTeacherById_Success() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("John Doe");
        teacher.setStudents(Collections.emptyList());
        teacher.setCourse(null);

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        teacherService.deleteTeacherById(teacher.getId());

        verify(teacherRepository).delete(teacher);
        verify(teacherRepository, times(1)).findById(teacher.getId());
        assert teacher.getStudents().isEmpty();
        assert teacher.getCourse() == null;
    }

    @Test
    void deleteTeacherById_TeacherNotFound() {
        Long teacherId = 1L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> teacherService.deleteTeacherById(teacherId));

        verify(teacherRepository, never()).delete(any());
    }

    @Test
    void getTeacherWithStudents_Success() {
        // Arrange
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        teacher.setName("John Doe");
        teacher.setStatus(StatusUser.TEACHER);

        Student student = new Student();
        student.setId(2L);
        student.setName("Jane Smith");
        student.setStatus(StatusUser.STUDENT);

        StudentDataDTO studentDataDTO = new StudentDataDTO(2L, "Jane Smith", StatusUser.STUDENT);
        TeacherWithStudentsDataDTO expectedTeacherDTO = new TeacherWithStudentsDataDTO(teacherId, "John Doe", StatusUser.TEACHER, List.of(studentDataDTO));

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));
        when(studentRepository.findStudentByTeacherId(teacherId)).thenReturn(List.of(student));
        when(studentMapper.map(student)).thenReturn(studentDataDTO);

        TeacherWithStudentsDataDTO result = teacherService.getTeacherWithStudents(teacherId);

        assertEquals(expectedTeacherDTO.getId(), result.getId());
        assertEquals(expectedTeacherDTO.getName(), result.getName());
        assertEquals(expectedTeacherDTO.getStatus(), result.getStatus());
        assertEquals(expectedTeacherDTO.getStudents(), result.getStudents());

        verify(teacherRepository, times(1)).findById(teacherId);
        verify(studentRepository, times(1)).findStudentByTeacherId(teacherId);
    }

    @Test
    void getTeacherWithStudents_TeacherNotFound() {
        Long teacherId = 1L;
        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        EntityNotFoundException thrown = assertThrows(EntityNotFoundException.class, () -> {
            teacherService.getTeacherWithStudents(teacherId);
        });

        assertEquals("Teacher with id 1 id not found", thrown.getMessage());
        verify(teacherRepository, times(1)).findById(teacherId);
        verify(studentRepository, never()).findStudentByTeacherId(anyLong());
    }

    private Teacher createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("Oleg")
                .status(StatusUser.TEACHER)
                .build();
        teacher.setId(1L);

        return teacher;
    }
}

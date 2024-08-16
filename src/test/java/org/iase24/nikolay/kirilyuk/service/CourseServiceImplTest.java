package org.iase24.nikolay.kirilyuk.service;

import jakarta.persistence.EntityNotFoundException;
import org.iase24.nikolay.kirilyuk.controller.exception.CourseNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.*;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.mapper.CourseMapper;
import org.iase24.nikolay.kirilyuk.mapper.StudentMapper;
import org.iase24.nikolay.kirilyuk.repository.CourseRepository;
import org.iase24.nikolay.kirilyuk.repository.StudentRepository;
import org.iase24.nikolay.kirilyuk.repository.TeacherRepository;
import org.iase24.nikolay.kirilyuk.service.impl.CourseServiceImpl;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    CourseMapper courseMapper;

    @Mock
    StudentMapper studentMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCourses_Success() throws Exception {
        Course course1 = createCourse();

        Course course2 = Course.builder()
                .name("IT-OverOne")
                .build();
        course2.setId(2L);

        when(courseRepository.findAll()).thenReturn(Arrays.asList(course1, course2));

        List<CourseDataDTO> result = courseService.getAllCourse();

        assertEquals(2, result.size());
        assertEquals("Aston", result.get(0).getName());
        assertEquals("IT-OverOne", result.get(1).getName());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void getCourseById_Return_By_Id() throws Exception, CourseNotFoundException {
        Course course = createCourse();

        CourseDataDTO courseDataDTO = new CourseDataDTO(course.getId(), course.getName());

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseMapper.map(course)).thenReturn(courseDataDTO);

        CourseDataDTO result = courseService.getCourseById(course.getId());

        assertEquals(1L, result.getId());
        assertEquals("Aston", result.getName());
    }

    @Test
    void getCourseById_Return_Not_Found() throws Exception, CourseNotFoundException {
        Long id = 1L;

        when(courseRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CourseNotFoundException.class, () -> courseService.getCourseById(id));
    }

    @Test
    void addCourse_Create_Course_Success() throws Exception {
        Course course = createCourse();

        when(courseRepository.save(course)).thenReturn(course);

        Course result = courseService.addCourse(course);

        assertEquals(course.getId(), result.getId());
        assertEquals("Aston", result.getName());
    }

    @Test
    void deleteCourseById_Delete_Course_Success() throws Exception {
        Course course = createCourse();

        // Убираем связь студентов с учителем
        course.getStudents().forEach(student -> student.setTeacher(null));

        // Убираем связь учителей курсом
        course.getTeachers().forEach(teacher -> teacher.setCourse(null));

        // Очищаем студентов в курсе
        course.getStudents().clear();

        // Удаляем уже пустой курс по его ID
        courseRepository.delete(course);
    }

    @Test
    void deleteCourseById_Delete_Course_NotFound() throws Exception {
        Course course = createCourse();

        courseRepository.delete(course);

        assertThrows(EntityNotFoundException.class, () -> courseService.deleteCourseById(course.getId()));

        verify(courseRepository, times(1)).delete(course);
    }

    @Test
    void updateCourse_Update_Course_Success() throws Exception, CourseNotFoundException {
        Course originCourse = createCourse();

        CourseDataDTO updateCourseData = new CourseDataDTO(originCourse.getId(), "Update course name");

        when(courseRepository.findById(originCourse.getId())).thenReturn(Optional.of(originCourse));

        courseService.updateCourse(originCourse.getId(), updateCourseData);

        assertEquals("Update course name", originCourse.getName());

        verify(courseRepository, times(1)).save(originCourse);
    }

    @Test
    void updateCourse_Course_NotFound() {
        Course course = createCourse();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        CourseDataDTO updateCourseData = new CourseDataDTO(course.getId(), "Update course name");

        assertThrows(CourseNotFoundException.class, () -> courseService.updateCourse(course.getId(), updateCourseData));
    }

    @Test
    void addTeacherToCourse_Course_NotFound() throws Exception {
        Course course = createCourse();
        Teacher teacher = createTeacher();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());
        when(courseRepository.findById(teacher.getId())).thenReturn(Optional.of(course));

        assertThrows(EntityNotFoundException.class, () -> courseService.addTeacherToCourse(course.getId(), teacher.getId()));
    }

    @Test
    void addTeacherToCourse_Teacher_NotFound() throws Exception {
        Course course = createCourse();
        Teacher teacher = createTeacher();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepository.findById(teacher.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.addTeacherToCourse(course.getId(), teacher.getId()));
    }

    @Test
    void addTeacherToCourse_Success() throws Exception {
        Course course = createCourse();
        Teacher teacher = createTeacher();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        courseService.addTeacherToCourse(course.getId(), teacher.getId());

        verify(teacherRepository, times(1)).save(teacher);
        verify(courseRepository, times(1)).findById(course.getId());
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void addStudentToTeacher_Student_NotFound() throws Exception {
        Teacher teacher = createTeacher();
        Student student = createStudent();

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.addStudentToTeacher(teacher.getId(), student.getId()));
    }

    @Test
    void addStudentToTeacher_Teacher_NotFound() throws Exception {
        Teacher teacher = createTeacher();
        Student student = createStudent();

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.empty());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        assertThrows(EntityNotFoundException.class, () -> courseService.addStudentToTeacher(teacher.getId(), student.getId()));
    }

    @Test
    void addStudentToTeacher_Success() throws Exception {
        Teacher teacher = createTeacher();
        Student student = createStudent();

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        courseService.addStudentToTeacher(student.getId(), teacher.getId());

        verify(teacherRepository, times(1)).save(teacher);
        verify(studentRepository, times(1)).save(student);
        verify(studentRepository, times(1)).findById(student.getId());
        verify(teacherRepository, times(1)).findById(teacher.getId());
    }

    @Test
    void getCourseByIdWithTeachers_Course_NotFound() throws Exception {
        Course course = createCourse();

        when(courseRepository.findById(course.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.getCourseByIdWithTeachers(course.getId()));
        assertEquals("Aston", course.getName());

        verify(courseRepository, times(1)).findById(course.getId());
    }

    @Test
    void getCourseByIdWithTeachers_Success() throws Exception {
        // Создание тестовых данных
        Course course = createCourse();
        course.setId(1L);

        Teacher teacher1 = createTeacher();
        teacher1.setId(1L);
        teacher1.setStudents(List.of(createStudent()));

        Teacher teacher2 = createTeacher();
        teacher2.setId(2L);
        teacher2.setStudents(List.of(createStudent()));

        List<Teacher> teachers = Arrays.asList(teacher1, teacher2);

        // Настройка моков
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(courseRepository.findByTeachersByCourseId(course.getId())).thenReturn(teachers);

        // Выполнение метода
        CourseWithTeachersDataDTO result = courseService.getCourseByIdWithTeachers(course.getId());

        // Проверка результата
        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        assertEquals(course.getName(), result.getName());
        assertEquals(2, result.getTeachers().size());

        // Проверяем, что первый учитель правильно преобразован в DTO
        TeacherWithStudentsDataDTO teacher1DTO = result.getTeachers().get(0);
        assertEquals(teacher1.getId(), teacher1DTO.getId());
        assertEquals(teacher1.getName(), teacher1DTO.getName());
        assertEquals(teacher1.getStatus(), teacher1DTO.getStatus());
        assertEquals(1, teacher1DTO.getStudents().size());

        // Проверяем, что второй учитель правильно преобразован в DTO
        TeacherWithStudentsDataDTO teacher2DTO = result.getTeachers().get(1);
        assertEquals(teacher2.getId(), teacher2DTO.getId());
        assertEquals(teacher2.getName(), teacher2DTO.getName());
        assertEquals(teacher2.getStatus(), teacher2DTO.getStatus());
        assertEquals(1, teacher2DTO.getStudents().size());

        // Проверяем, что студенты преобразованы в DTO
        StudentDataDTO studentDTO = teacher1DTO.getStudents().get(0);
        assertNotNull(studentDTO);
        assertEquals(teacher1.getStudents().get(0).getId(), studentDTO.getId());
        assertEquals(teacher1.getStudents().get(0).getName(), studentDTO.getName());
        assertEquals(teacher1.getStudents().get(0).getStatus(), studentDTO.getStatus());
    }

    @Test
    void getAllStudentByCourseId_Success() {
        // Создание тестовых данных
        Long courseId = 1L;
        Course course = createCourse();
        course.setId(courseId);

        Student student1 = createStudent();
        student1.setId(1L);
        Student student2 = createStudent();
        student2.setId(2L);

        List<Student> students = Arrays.asList(student1, student2);

        // Создание DTO для студентов
        StudentDataDTO studentDataDTO1 = new StudentDataDTO(student1.getId(), student1.getName(), student1.getStatus());
        StudentDataDTO studentDataDTO2 = new StudentDataDTO(student2.getId(), student2.getName(), student2.getStatus());

        // Настройка моков
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));
        when(studentRepository.findStudentByCourseId(courseId)).thenReturn(students);
        when(studentMapper.map(student1)).thenReturn(studentDataDTO1);
        when(studentMapper.map(student2)).thenReturn(studentDataDTO2);

        // Выполнение метода
        CourseWithStudentsDataDTO result = courseService.getAllStudentByCourseId(courseId);

        // Проверка результата
        assertNotNull(result);
        assertEquals(course.getId(), result.getId());
        assertEquals(course.getName(), result.getName());
        assertEquals(2, result.getStudents().size());

        // Проверяем первый студент
        StudentDataDTO resultStudentDTO1 = result.getStudents().get(0);
        assertEquals(studentDataDTO1.getId(), resultStudentDTO1.getId());
        assertEquals(studentDataDTO1.getName(), resultStudentDTO1.getName());
        assertEquals(studentDataDTO1.getStatus(), resultStudentDTO1.getStatus());

        // Проверяем второй студент
        StudentDataDTO resultStudentDTO2 = result.getStudents().get(1);
        assertEquals(studentDataDTO2.getId(), resultStudentDTO2.getId());
        assertEquals(studentDataDTO2.getName(), resultStudentDTO2.getName());
        assertEquals(studentDataDTO2.getStatus(), resultStudentDTO2.getStatus());

        // Проверка взаимодействия с моками
        verify(courseRepository, times(1)).findById(courseId);
        verify(studentRepository, times(1)).findStudentByCourseId(courseId);
        verify(studentMapper, times(1)).map(student1);
        verify(studentMapper, times(1)).map(student2);
    }

    private Course createCourse() {
        Course course = Course.builder()
                .name("Aston")
                .students(new ArrayList<>())  // Инициализация пустого списка студентов
                .teachers(new ArrayList<>())
                .build();
        course.setId(1L);

        return course;
    }

    @Test
    void deleteStudentFromTeacher_Success() {
        Teacher teacher = createTeacher();
        Student student = createStudent();
        student.setTeacher(teacher);
        teacher.getStudents().add(student); // Добавляем студента к учителю

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        courseService.deleteStudentFromTeacher(teacher.getId(), student.getId());

        verify(studentRepository, times(1)).save(argThat(savedStudent ->
                savedStudent.getTeacher() == null && savedStudent.getId().equals(student.getId())
        ));
        assertNull(student.getTeacher(), "Student's teacher should be null after deletion");
    }


    private Teacher createTeacher() {
        Teacher teacher = Teacher.builder()
                .name("Oleg")
                .status(StatusUser.TEACHER)
                .course(null)
                .students(new ArrayList<>())
                .build();
        teacher.setId(2L);

        return teacher;
    }

    private Student createStudent() {
        Student student = Student.builder()
                .name("Nikolay")
                .status(StatusUser.STUDENT)
                .courses(new ArrayList<>())
                .teacher(null)
                .build();
        student.setId(3L);

        return student;
    }
}

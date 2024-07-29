package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithTeachersDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Tag(name = "Архив курсов", description = "Получение результатов для проверки работоспособности")
public class CourseController {

    private final CourseService courseService;

    @Operation(
            description = "Получить список всех курсов",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping
    public List<CourseDataDTO> getAllCourse() {
        return courseService.getAllCourse();
    }

    @Operation(
            description = "Получить курс по ID",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/{id}")
    public CourseDataDTO getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @Operation(
            description = "Создать новый курс",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @PostMapping
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @Operation(
            description = "Добавить учителя на курс",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping("/courseId/{courseId}/teacherId/{teacherId}")
    public void addTeacherToCourse(
            @PathVariable("courseId") Long courseId,
            @PathVariable("teacherId") Long teacherId
    ) {
        courseService.addTeacherToCourse(courseId, teacherId);
    }

    @Operation(
            description = "Добавить ученика к учителю по ID студента и учителя",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping("/studentId/{studentId}/to/teacherId/{teacherId}")
    public void addStudentToTeacher(
            @PathVariable("studentId") Long studentId,
            @PathVariable("teacherId") Long teacherId
    ) {
        courseService.addStudentToTeacher(studentId, teacherId);
    }

    @Operation(
            description = "Обновть название курса",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") Long id, @RequestBody CourseDataDTO courseDataDTO) {
        courseService.updateCourse(id, courseDataDTO);
    }

    @Operation(
            description = "Удалить курс по ID",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
    }

    @Operation(
            description = "Вывести всю информацию о курсе по его ID",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/{courseId}/teachers")
    public CourseWithTeachersDataDTO courseByIdWithTeachers(@PathVariable("courseId") Long courseId) {
        return courseService.getCourseByIdWithTeachers(courseId);
    }

    @Operation(
            description = "Убрать ученика из группы конкретного учителя по их ID",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @PutMapping("/delete-from-teacherId/{teacherId}/studentId/{studentId}")
    public void removeStudentFromTeacher(
            @PathVariable("teacherId") Long teacherId,
            @PathVariable("studentId") Long studentId
    ) {
        courseService.deleteStudentFromTeacher(teacherId, studentId);
    }

    @Operation(
            description = "Показать курс по ID со всеми студентами",
            summary = "Сводная информация endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Invalid",
                            responseCode = "500"
                    )
            }
    )
    @GetMapping("/{courseId}/with-students")
    public CourseWithStudentsDataDTO getAllStudentByCourseId(@PathVariable("courseId") Long courseId) {
        return courseService.getAllStudentByCourseId(courseId);
    }
}

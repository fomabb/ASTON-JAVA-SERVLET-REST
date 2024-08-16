package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.controller.exception.CourseNotFoundException;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithTeachersDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.model.out.ErrorRestOut;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Tag(name = "Архив курсов", description = "Получение результатов для проверки работоспособности")
public class CourseController {

    private final CourseService courseService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Получить список всех курсов",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping
    public List<CourseDataDTO> getAllCourse() {
        return courseService.getAllCourse();
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Получить курс по ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping("/{id}")
    public CourseDataDTO getCourseById(@PathVariable("id") Long id) throws CourseNotFoundException {
        return courseService.getCourseById(id);
    }

    @Operation(
            description = "Создать новый курс",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        Course createdCourse = courseService.addCourse(course);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Добавить учителя на курс",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PutMapping("/courseId/{courseId}/teacherId/{teacherId}")
    public void addTeacherToCourse(
            @PathVariable("courseId") Long courseId,
            @PathVariable("teacherId") Long teacherId
    ) {
        try {
            courseService.addTeacherToCourse(courseId, teacherId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Добавить ученика к учителю по ID студента и учителя",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PutMapping("/studentId/{studentId}/to/teacherId/{teacherId}")
    public void addStudentToTeacher(
            @PathVariable("studentId") Long studentId,
            @PathVariable("teacherId") Long teacherId
    ) {
        try {
            courseService.addStudentToTeacher(studentId, teacherId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Обновть название курса",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") Long id, @RequestBody CourseDataDTO courseDataDTO) throws CourseNotFoundException {
        courseService.updateCourse(id, courseDataDTO);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Удалить курс по ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable("id") Long id) {
        try {
            courseService.deleteCourseById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Вывести всю информацию о курсе по его ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping("/{courseId}/teachers")
    public CourseWithTeachersDataDTO courseByIdWithTeachers(@PathVariable("courseId") Long courseId) {
        try {
            return courseService.getCourseByIdWithTeachers(courseId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Убрать ученика из группы конкретного учителя по их ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PutMapping("/delete-from-teacherId/{teacherId}/studentId/{studentId}")
    public void removeStudentFromTeacher(
            @PathVariable("teacherId") Long teacherId,
            @PathVariable("studentId") Long studentId
    ) {
        courseService.deleteStudentFromTeacher(teacherId, studentId);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Показать курс по ID со всеми студентами",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping("/{courseId}/with-students")
    public CourseWithStudentsDataDTO getAllStudentByCourseId(@PathVariable("courseId") Long courseId) {

        try {
            return courseService.getAllStudentByCourseId(courseId);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}

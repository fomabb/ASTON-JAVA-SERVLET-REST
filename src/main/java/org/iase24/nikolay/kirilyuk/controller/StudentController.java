package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.model.out.ErrorRestOut;
import org.iase24.nikolay.kirilyuk.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "Архив студентов", description = "Получение результатов для проверки работоспособности")
public class StudentController {

    private final StudentService studentService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Получить список всех студентов",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping
    public List<StudentDataDTO> getAllStudents() {
        return studentService.getAllStudent();
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Получить студентоа по ID",
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
    public StudentDataDTO findStudentById(@PathVariable("id") Long id) {
        try {
            return studentService.getStudentById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Добавит студента",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PostMapping
    public ResponseEntity<List<Student>> addStudent(@RequestBody List<Student> students) {
        studentService.addStudent(students);
        return ResponseEntity.ok(students);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Удалить студента по ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @DeleteMapping("/{id}")
    public void deleteStudentById(@PathVariable("id") Long id) {
        try {
            studentService.deleteStudent(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Добавить студента на курс",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PostMapping("/add/studentId/{studentId}/to/courseId/{courseId}")
    public ResponseEntity<Course> addStudentToCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {

       Course course = studentService.addStudentToCourse(studentId, courseId);

       return ResponseEntity.ok(course);
    }
}

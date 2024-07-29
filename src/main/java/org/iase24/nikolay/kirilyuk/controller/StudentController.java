package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@Tag(name = "Архив студентов", description = "Получение результатов для проверки работоспособности")
public class StudentController {

    private final StudentService studentService;

    @Operation(
            description = "Получить список всех студентов",
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
    public List<StudentDataDTO> getAllStudents() {
        return studentService.getAllStudent();
    }

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
        return studentService.getStudentById(id);
    }

    @Operation(
            description = "Добавит студента",
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
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @Operation(
            description = "Удалить студента по ID",
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
    public void deleteStudentById(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }

    @Operation(
            description = "Добавить студента на курс",
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
    @PostMapping("/add/studentId/{studentId}/to/courseId/{courseId}")
    public void addStudentToCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId) {
        studentService.addStudentToCourse(studentId, courseId);
    }
}

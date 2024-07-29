package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Tag(name = "Архив учителей", description = "Получение результатов для проверки работоспособности")
public class TeacherController {

    private final TeacherService teacherService;

    @Operation(
            description = "Получить список всех учителей",
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
    public List<TeacherDataDTO> getTeachers() {
        return teacherService.getAllTeachers();
    }

    @Operation(
            description = "Найти учителя по ID",
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
    public TeacherDataDTO getTeacher(@PathVariable("id") Long id) {
        return teacherService.getTeacherById(id);
    }

    @Operation(
            description = "Добавить учителя",
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
    public void addTeacher(@RequestBody Teacher teacher) {
        teacherService.addTeacher(teacher);
    }

    @Operation(
            description = "Удалить учителя по ID",
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
    public void deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacherById(id);
    }

    @Operation(
            description = "Показать учителя по ID со всеми его студентами",
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
    @GetMapping("/{id}/with-students")
    public TeacherWithStudentsDataDTO getTeacherWithStudents(@PathVariable("id") Long id) {
        return teacherService.getTeacherWithStudents(id);
    }
}

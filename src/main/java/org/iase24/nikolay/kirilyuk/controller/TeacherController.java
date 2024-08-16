package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.dto.TeacherWithStudentsDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.model.out.ErrorRestOut;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
@Tag(name = "Архив учителей", description = "Получение результатов для проверки работоспособности")
public class TeacherController {

    private final TeacherService teacherService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Получить список всех учителей",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping
    public List<TeacherDataDTO> getTeachers() {
        return teacherService.getAllTeachers();
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Найти учителя по ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping("/{id}")
    public TeacherDataDTO getTeacher(@PathVariable("id") Long id) {

        try {
            return teacherService.getTeacherById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Добавить учителя",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @PostMapping
    public void addTeacher(@RequestBody List<Teacher> teachers) {
        teacherService.addTeacher(teachers);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Удалить учителя по ID",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable("id") Long id) {
        teacherService.deleteTeacherById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(
            description = "Показать учителя по ID со всеми его студентами",
            summary = "Сводная информация endpoint"
    )
    @ApiResponse(responseCode = "200", description = "Операция выполнена успешно")
    @ApiResponse(responseCode = "400", description = "Некоректный формат запроса",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class)))
    @ApiResponse(responseCode = "500", description = "Внутренняя сервера",
            content = @Content(schema = @Schema(implementation = ErrorRestOut.class))
    )
    @GetMapping("/{id}/with-students")
    public TeacherWithStudentsDataDTO getTeacherWithStudents(@PathVariable("id") Long id) {
        return teacherService.getTeacherWithStudents(id);
    }
}

package org.iase24.nikolay.kirilyuk.controller;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.service.TeacherService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping
    public List<TeacherDataDTO> getTeachers(){
        return teacherService.getAllTeachers();
    }

    @GetMapping("/{id}")
    public TeacherDataDTO getTeacher(@PathVariable("id") Long id){
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    public void addTeacher(@RequestBody Teacher teacher){
        teacherService.addTeacher(teacher);
    }

    @DeleteMapping("/{id}")
    public void deleteTeacher(@PathVariable("id") Long id){
        teacherService.deleteTeacherById(id);
    }
}

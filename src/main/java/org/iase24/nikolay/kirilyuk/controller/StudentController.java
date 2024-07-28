package org.iase24.nikolay.kirilyuk.controller;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<StudentDataDTO> getAllStudents() {
        return studentService.getAllStudent();
    }

    @GetMapping("/{id}")
    public StudentDataDTO findStudentById(@PathVariable("id") Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping("/id")
    public void deleteStudentById(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
    }
}

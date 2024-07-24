package org.iase24.nikolay.kirilyuk.controller;

import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/test")
    public String hello() {
        return "<h1>J9 SDELAL ETO NAKONEC! CONFIG IS EXIST</h1>";
    }

    @GetMapping
    public List<CourseDataDTO> getAllCourse() {
        return courseService.getAllCourse();
    }

    @GetMapping("/{id}")
    public CourseDataDTO getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @PostMapping("/courseId/{courseId}/teacherId/{teacherId}")
    public void addTeacherToCourse(@PathVariable("courseId") Long courseId, @PathVariable("teacherId") Long teacherId) {
        courseService.addTeacherToCourse(courseId, teacherId);
    }

    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") Long id, @RequestBody CourseDataDTO courseDataDTO) {
        courseService.updateCourse(id, courseDataDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
    }
}

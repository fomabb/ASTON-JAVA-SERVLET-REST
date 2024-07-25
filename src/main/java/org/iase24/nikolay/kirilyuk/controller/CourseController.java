package org.iase24.nikolay.kirilyuk.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.dto.CourseWithTeachersDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
@Tag(name = "Course Controller", description = "API for managing courses")
public class CourseController {

    private final CourseService courseService;

    @GetMapping("/test")
    public String hello() {
        return "<h1>J9 SDELAL ETO NAKONEC! CONFIG IS EXIST</h1>";
    }

    @Operation(summary = "Get all courses")
    @GetMapping
    public List<CourseDataDTO> getAllCourse() {
        return courseService.getAllCourse();
    }

    @Operation(summary = "Get course by ID")
    @GetMapping("/{id}")
    public CourseDataDTO getCourseById(@PathVariable("id") Long id) {
        return courseService.getCourseById(id);
    }

    @Operation(summary = "Add a new course")
    @PostMapping
    public void addCourse(@RequestBody Course course) {
        courseService.addCourse(course);
    }

    @Operation(summary = "Add teacher to course")
    @PutMapping("/courseId/{courseId}/teacherId/{teacherId}")
    public void addTeacherToCourse(
            @PathVariable("courseId") Long courseId,
            @PathVariable("teacherId") Long teacherId
    ) {
        courseService.addTeacherToCourse(courseId, teacherId);
    }

    @Operation(summary = "Update name course by course ID")
    @PutMapping("/{id}")
    public void updateCourse(@PathVariable("id") Long id, @RequestBody CourseDataDTO courseDataDTO) {
        courseService.updateCourse(id, courseDataDTO);
    }

    @Operation(summary = "Delete course by ID")
    @DeleteMapping("/{id}")
    public void deleteCourseById(@PathVariable("id") Long id) {
        courseService.deleteCourseById(id);
    }

    @Operation(summary = "Get course with teachers by course ID")
    @GetMapping("/teachers/{courseId}")
    public CourseWithTeachersDataDTO courseByIdWithTeachers(@PathVariable("courseId") Long courseId) {
        return courseService.getCourseByIdWithTeachers(courseId);
    }

    @Operation(summary = "Remove a student from a teacher")
    @PutMapping("/delete-from-teacherId/{teacherId}/studentId/{studentId}")
    public void removeStudentFromTeacher(
            @PathVariable("teacherId") Long teacherId,
            @PathVariable("studentId") Long studentId
    ) {
        courseService.deleteStudentFromTeacher(teacherId, studentId);
    }
}

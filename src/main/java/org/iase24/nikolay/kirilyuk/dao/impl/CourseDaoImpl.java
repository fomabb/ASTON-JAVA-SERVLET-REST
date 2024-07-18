package org.iase24.nikolay.kirilyuk.dao.impl;

import org.iase24.nikolay.kirilyuk.dao.CourseDao;
import org.iase24.nikolay.kirilyuk.model.Course;
import org.iase24.nikolay.kirilyuk.model.Student;
import org.iase24.nikolay.kirilyuk.model.Teacher;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private static final String GET_ALL_COURSES = "SELECT * FROM course";
    private static final String GET_COURSE_BY_ID = "SELECT * FROM course WHERE id = ?";
    private static final String ADD_NEW_COURSE = "INSERT INTO course (name) VALUES (?)";
    private static final String DELETE_COURSE = "DELETE FROM course WHERE id = ?";
    private static final String UPDATE_COURSE = "UPDATE course SET name = ? WHERE id = ?";
    private static final String ADD_TEACHER_IN_COURSE = "update course set teacher_id = ? where id = ?";
    private static final String GET_STUDENT_BY_COURSE_ID =
            "SELECT s.id, s.name, s.status FROM student s " +
                    "join students_courses sc on s.id = sc.student_id " +
                    "where sc.course_id = ?";
    private static final String GET_TEACHER_BY_TEACHER_ID =
            "SELECT t.id, t.name, t.status FROM teacher t " +
                    "join course c on t.id = c.teacher_id " +
                    "where c.id = ?";

    @Override
    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_COURSES);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getLong("id"));
                course.setName(resultSet.getString("name"));
                courses.add(course);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return courses;
    }

    @Override
    public Course getCourseById(Long id) {
        Course course = null;
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_COURSE_BY_ID)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long courseId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                List<Student> students = getStudentByCourseId(courseId);
                Teacher teacher = getTeacherByTeacherId(courseId);

                course = new Course(courseId, name, students, teacher);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    @Override
    public void addCourse(Course course) {
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_NEW_COURSE, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, course.getName());
            statement.executeUpdate();
            try (ResultSet generateKey = statement.getGeneratedKeys()) {
                if (generateKey.next()) {
                    course.setId(generateKey.getLong(1));
                } else {
                    throw new SQLException("Creating course failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCourse(Long id) {
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_COURSE)
        ) {
            if (id != null) {
                statement.setLong(1, id);
            } else {
                throw new SQLException("Deleting course failed, no ID obtained.");
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCourse(Course course, Long id) {
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE)
        ) {
            statement.setString(1, course.getName());
            if (id != null) {
                statement.setLong(2, id);
            } else {
                throw new SQLException("Updating course failed, no ID obtained.");
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTeacherInCourse(Long courseId, Integer teacherId) {
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(ADD_TEACHER_IN_COURSE)
        ) {
            statement.setLong(1, courseId);
            statement.setInt(2, teacherId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Teacher getTeacherByTeacherId(Long courseId) {
        Teacher teacher = null;
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_TEACHER_BY_TEACHER_ID)
        ) {
            statement.setLong(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long teacherId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                StatusUser status = StatusUser.valueOf(resultSet.getString("status"));
                List<Student> students = getStudentByCourseId(courseId);
                teacher = new Teacher(teacherId, name, status, null, students);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    private List<Student> getStudentByCourseId(Long courseId) {
        List<Student> students = new ArrayList<>();
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_STUDENT_BY_COURSE_ID)
        ) {

            statement.setLong(1, courseId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long studentId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                StatusUser status = StatusUser.valueOf(resultSet.getString("status"));

                students.add(new Student(studentId, name, status, null, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

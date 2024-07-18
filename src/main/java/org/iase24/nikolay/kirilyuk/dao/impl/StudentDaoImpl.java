package org.iase24.nikolay.kirilyuk.dao.impl;

import org.iase24.nikolay.kirilyuk.dao.StudentDao;
import org.iase24.nikolay.kirilyuk.model.Course;
import org.iase24.nikolay.kirilyuk.model.Student;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private static final String GET_ALL_USER = "SELECT * FROM student";
    private static final String ADD_NEW_STUDENT = "INSERT INTO student (name, status) values (?, ?)";
    private static final String UPDATE_STUDENT = "UPDATE students SET name = ? WHERE id = ?";
    private static final String DELETE_STUDENT = "DELETE FROM students WHERE id = ?";
    private static final String GET_STUDENT_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String GET_COURSE_BY_ID =
            "SELECT c.id, c.name FROM course c " +
                    "INNER JOIN students_courses sc ON c.id = sc.course_id " +
                    "WHERE sc.student_id = ?";

    @Override
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_USER);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                student.setStatus(StatusUser.valueOf(resultSet.getString("status")));
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void addStudent(Student student) {
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_NEW_STUDENT,
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, student.getName());
            student.setStatus(StatusUser.STUDENT);
            statement.setString(2, student.getStatus().name());
            statement.executeUpdate();

            try (ResultSet generateKey = statement.getGeneratedKeys()) {
                if (generateKey.next()) {
                    student.setId(generateKey.getLong(1));
                } else {
                    throw new SQLException("Creating student failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateStudent(Student student) {

    }

    @Override
    public void deleteStudent(Student student) {

    }

    @Override
    public Student getStudentById(Long id) {
        Student student = null;
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_STUDENT_BY_ID);
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long studentId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                StatusUser status = StatusUser.valueOf(resultSet.getString("status"));

                List<Course> courses = getCoursesByStudentId(studentId);

                student = new Student(studentId, name, status, null, courses);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return student;
    }

    private List<Course> getCoursesByStudentId(Long studentId) {
        List<Course> courses = new ArrayList<>();

        try (PreparedStatement statement = DataBaseConnection.getConnection().prepareStatement(GET_COURSE_BY_ID)) {
            statement.setLong(1, studentId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long courseId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                courses.add(new Course(courseId, name, null, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}

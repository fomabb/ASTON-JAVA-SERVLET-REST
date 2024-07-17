package org.iase24.nikolay.kirilyuk.dao.impl;

import org.iase24.nikolay.kirilyuk.dao.TeacherDao;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {

    private static final String GET_ALL_TEACHERS = "SELECT * FROM teacher";
    private static final String ADD_NEW_TEACHER = "INSERT INTO teacher (name, status) values (?, ?)";
    private static final String GET_TEACHER_BY_ID = "SELECT * FROM teacher WHERE id = ?";
    private static final String GET_STUDENT_BY_TEACHER_ID =
            "SELECT * FROM student s " +
                    "INNER JOIN teacher t ON s.id = teacher_id";

    @Override
    public List<Teacher> getAllTeachers() {
        List<Teacher> teachers = new ArrayList<>();

        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_ALL_TEACHERS);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getLong("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setStatus(StatusUser.valueOf(resultSet.getString("status")));
                teachers.add(teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }

    @Override
    public void addTeacher(Teacher teacher) {
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        ADD_NEW_TEACHER, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, teacher.getName());
            teacher.setStatus(StatusUser.TEACHER);
            statement.setString(2, teacher.getStatus().toString());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    teacher.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating teacher failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Teacher getTeacherById(Long id) {
        Teacher teacher = null;
        try (
                Connection connection = DataBaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(GET_TEACHER_BY_ID)
        ) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long teacherId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                StatusUser status = StatusUser.valueOf(resultSet.getString("status"));
                String course = resultSet.getString("course");

                List<Student> students = getStudentByTeacherId(teacherId);

                teacher = new Teacher(teacherId, name, status, null, students);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teacher;
    }

    private List<Student> getStudentByTeacherId(Long teacherId) {
        List<Student> students = new ArrayList<>();

        try (
                PreparedStatement statement = DataBaseConnection.getConnection()
                        .prepareStatement(GET_STUDENT_BY_TEACHER_ID)
        ) {
            statement.setLong(1, teacherId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long studentId = resultSet.getLong("id");
                String name = resultSet.getString("name");
                StatusUser status = StatusUser.valueOf(resultSet.getString("status"));
                String teacherName = resultSet.getString("teacher");
                String course = resultSet.getString("course");
                students.add(new Student(studentId, name, status, null, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }
}

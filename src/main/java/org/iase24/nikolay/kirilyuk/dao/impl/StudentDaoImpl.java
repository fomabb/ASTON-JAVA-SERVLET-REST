package org.iase24.nikolay.kirilyuk.dao.impl;

import org.iase24.nikolay.kirilyuk.dao.StudentDao;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private static final String GET_ALL_USER = "SELECT * FROM students";
    private static final String ADD_NEW_STUDENT = "INSERT INTO students (name) values (?)";

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
}

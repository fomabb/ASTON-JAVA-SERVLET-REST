package org.iase24.nikolay.kirilyuk.dao.impl;

import org.iase24.nikolay.kirilyuk.dao.TeacherDao;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl implements TeacherDao {

    private static final String GET_ALL_TEACHERS = "SELECT * FROM teachers";
    private static final String ADD_NEW_TEACHER = "INSERT INTO teachers (name) values (?)";

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
}

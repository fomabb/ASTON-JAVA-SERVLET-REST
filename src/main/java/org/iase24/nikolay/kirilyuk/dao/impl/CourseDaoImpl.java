package org.iase24.nikolay.kirilyuk.dao.impl;

import org.iase24.nikolay.kirilyuk.dao.CourseDao;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private static final String GET_ALL_COURSES = "SELECT * FROM course";
    private static final String ADD_NEW_COURSE = "INSERT INTO course (name) VALUES (?)";

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
}

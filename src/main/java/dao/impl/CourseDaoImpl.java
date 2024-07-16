package dao.impl;

import dao.CourseDao;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl implements CourseDao {

    private static final String GET_ALL_COURSES = "SELECT * FROM courses";

    @Override
    public List<Course> getAllCourse() {
        List<Course> courses = new ArrayList<>();

        try (
                Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_ALL_COURSES)) {
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
}

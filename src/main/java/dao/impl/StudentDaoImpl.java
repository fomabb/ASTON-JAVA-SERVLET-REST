package dao.impl;

import dao.StudentDao;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImpl implements StudentDao {

    private static final String GET_ALL_USER = "SELECT * FROM students";

    @Override
    public List<Student> getAllStudent() {
        List<Student> students = new ArrayList<>();
        try (
                Connection connection = DataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(GET_ALL_USER)) {
            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getLong("id"));
                student.setName(resultSet.getString("name"));
                students.add(student);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return students;
    }
}

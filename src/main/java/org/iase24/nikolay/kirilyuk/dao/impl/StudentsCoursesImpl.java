//package org.iase24.nikolay.kirilyuk.dao.impl;
//
//import org.iase24.nikolay.kirilyuk.dao.StudentsCoursesDao;
//import org.iase24.nikolay.kirilyuk.util.DataBaseConnection;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//
//public class StudentsCoursesImpl implements StudentsCoursesDao {
//
//    private static final String ADD_STUDENT_IN_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?,?)";
//    private static final String DELETE_STUDENT_IN_COURSE = "DELETE FROM students_courses WHERE student_id = ? and course_id = ?";
//
//    @Override
//    public void addStudentInCourse(Integer studentId, Integer courseId) {
//        try (
//                Connection connection = DataBaseConnection.getConnection();
//                PreparedStatement statement = connection.prepareStatement(ADD_STUDENT_IN_COURSE)
//        ) {
//            statement.setInt(1, studentId);
//            statement.setInt(2, courseId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void deleteStudentInCourse(Integer studentId, Integer courseId) {
//        try (
//                Connection connection = DataBaseConnection.getConnection();
//                PreparedStatement statement = connection.prepareStatement(DELETE_STUDENT_IN_COURSE)
//        ) {
//            statement.setInt(1, studentId);
//            statement.setInt(2, courseId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}

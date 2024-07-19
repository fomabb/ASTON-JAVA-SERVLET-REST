//package org.iase24.nikolay.kirilyuk.servlet;
//
//import com.google.gson.Gson;
//import org.iase24.nikolay.kirilyuk.dao.StudentDao;
//import org.iase24.nikolay.kirilyuk.dao.impl.StudentDaoImpl;
//import org.iase24.nikolay.kirilyuk.model.Student;
//import org.iase24.nikolay.kirilyuk.util.HttpResponseUtil;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.util.List;
//
//@WebServlet("/api/students")
//public class StudentServlet extends HttpServlet {
//
//    private final StudentDao studentDao;
//    private final HttpResponseUtil responseUtil;
//
//    public StudentServlet() {
//        this.responseUtil = new HttpResponseUtil();
//        this.studentDao = new StudentDaoImpl();
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        String idParam = req.getParameter("id");
//        if (idParam != null) {
//            try {
//                Long id = Long.parseLong(idParam);
//                Student student = studentDao.getStudentById(id);
//                if (student != null) {
//                    responseUtil.httpResponse(resp, student);
//                } else {
//                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
//                    responseUtil.httpResponse(resp, "Student not found with ID: " + id);
//                }
//            } catch (NumberFormatException e) {
//                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
//                responseUtil.httpResponse(resp, "Invalid ID format");
//            }
//        } else {
//            List<Student> students = studentDao.getAllStudent();
//            responseUtil.httpResponse(resp, students);
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        BufferedReader reader = req.getReader();
//        Gson gson = new Gson();
//        Student student = gson.fromJson(reader, Student.class);
//
//        studentDao.addStudent(student);
//        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
//        responseUtil.httpResponse(resp, student);
//    }
//
//    @Override
//    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
//        String idParam = req.getParameter("id");
//        Long id = Long.parseLong(idParam);
//        studentDao.deleteStudent(id);
//    }
//}

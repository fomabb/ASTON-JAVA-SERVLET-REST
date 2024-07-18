package org.iase24.nikolay.kirilyuk.servlet;

import org.iase24.nikolay.kirilyuk.dao.StudentsCoursesDao;
import org.iase24.nikolay.kirilyuk.dao.impl.StudentsCoursesImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/students/courses")
public class StudentsCoursesServlet extends HttpServlet {

    private final StudentsCoursesDao studentsCoursesDao;

    public StudentsCoursesServlet() {
        this.studentsCoursesDao = new StudentsCoursesImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idStudent = Integer.parseInt(req.getParameter("idStudent"));
        int idCourse = Integer.parseInt(req.getParameter("idCourse"));
        studentsCoursesDao.addStudentInCourse(idStudent, idCourse);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idStudent = Integer.parseInt(req.getParameter("idStudent"));
        int idCourse = Integer.parseInt(req.getParameter("idCourse"));
        studentsCoursesDao.deleteStudentInCourse(idStudent, idCourse);

    }
}

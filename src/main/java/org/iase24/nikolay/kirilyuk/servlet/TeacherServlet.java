package org.iase24.nikolay.kirilyuk.servlet;

import com.google.gson.Gson;
import org.iase24.nikolay.kirilyuk.dao.TeacherDao;
import org.iase24.nikolay.kirilyuk.dao.impl.TeacherDaoImpl;
import org.iase24.nikolay.kirilyuk.model.Teacher;
import org.iase24.nikolay.kirilyuk.util.HttpResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/teachers")
public class TeacherServlet extends HttpServlet {

    private final TeacherDao teacherDao;
    private final HttpResponseUtil responseUtil;


    public TeacherServlet() {
        this.responseUtil = new HttpResponseUtil();
        this.teacherDao = new TeacherDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");

        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                Teacher teacher = teacherDao.getTeacherById(id);
                if (teacher != null) {
                    responseUtil.httpResponse(resp, teacher);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    responseUtil.httpResponse(resp, "Teacher not found with ID: " + id);
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                responseUtil.httpResponse(resp, "Invalid ID format");
            }
        } else {
            List<Teacher> teachers = teacherDao.getAllTeachers();
            responseUtil.httpResponse(resp, teachers);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Teacher teacher = gson.fromJson(reader, Teacher.class);

        teacherDao.addTeacher(teacher);
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        responseUtil.httpResponse(resp, teacher);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String idParam = req.getParameter("id");
        Long id = Long.parseLong(idParam);
        teacherDao.deleteTeacherById(id);
    }
}

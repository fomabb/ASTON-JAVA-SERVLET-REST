package org.iase24.nikolay.kirilyuk.servlet;

import com.google.gson.Gson;
import org.iase24.nikolay.kirilyuk.dao.TeacherDao;
import org.iase24.nikolay.kirilyuk.dao.impl.TeacherDaoImpl;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.util.HttpResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/teachers")
public class TeacherServlet extends HttpServlet {

    private final TeacherDao teacherDao;
    private final HttpResponseUtil responseUtil;


    public TeacherServlet() {
        this.responseUtil = new HttpResponseUtil();
        this.teacherDao = new TeacherDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Teacher> teachers = teacherDao.getAllTeachers();
        responseUtil.httpResponse(resp, teachers);
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
}

package org.iase24.nikolay.kirilyuk.servlet;

import com.google.gson.Gson;
import org.iase24.nikolay.kirilyuk.dao.CourseDao;
import org.iase24.nikolay.kirilyuk.dao.impl.CourseDaoImpl;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.util.HttpResponseUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/courses")
public class CourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final CourseDao courseDao;
    private final HttpResponseUtil responseUtil;

    public CourseServlet() {
        this.courseDao = new CourseDaoImpl();
        this.responseUtil = new HttpResponseUtil();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                Long id = Long.parseLong(idParam);
                Course course = courseDao.getCourseById(id);
                if (course != null) {
                    responseUtil.httpResponse(resp, course);
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    responseUtil.httpResponse(resp, "Course not found with ID: " + id);
                }
            } catch (NullPointerException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                responseUtil.httpResponse(resp, "Invalid ID format");
            }
        } else {
            List<Course> courses = courseDao.getAllCourse();
            responseUtil.httpResponse(resp, courses);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        Gson gson = new Gson();
        Course course = gson.fromJson(reader, Course.class);
        courseDao.addCourse(course);
        responseUtil.httpResponse(resp, course);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Long id = Long.parseLong(idParam);
        courseDao.deleteCourse(id);

        resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        responseUtil.httpResponse(resp, courseDao.getAllCourse());
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        Long courseId = Long.parseLong(req.getParameter("courseId"));
        int teacherId = Integer.parseInt(req.getParameter("teacherId"));
        if (idParam != null) {
            Long id = Long.parseLong(idParam);
            BufferedReader reader = req.getReader();
            Gson gson = new Gson();
            Course course = gson.fromJson(reader, Course.class);

            courseDao.updateCourse(course, id);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            responseUtil.httpResponse(resp, course);
        } else {
            courseDao.addTeacherInCourse(courseId, teacherId);
        }
    }
}

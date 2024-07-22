package org.iase24.nikolay.kirilyuk.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iase24.nikolay.kirilyuk.dao.CourseDao;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.util.HibernateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CourseDaoImpl implements CourseDao {

    @Override
    @SuppressWarnings("uncheked")
    public List<CourseDataDTO> getAllCourse() {
        List<CourseDataDTO> coursesDataDTOs = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Course> courses = session.createQuery("from Course").list();
            coursesDataDTOs = courses.stream()
                    .map(course -> new CourseDataDTO(course.getId(), course.getName()))
                    .collect(Collectors.toList());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return coursesDataDTOs;
    }

    @Override
    public Course getCourseById(Long id) {
        Transaction transaction = null;
        Course courses = new Course();
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            courses = session.get(Course.class, id);
            Hibernate.initialize(courses.getStudentsCourses());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void addCourse(Course course) {
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            transaction = session.beginTransaction();
            session.save(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void deleteCourse(Long id) {
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            session.beginTransaction();
            session.delete(getCourseById(id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void updateCourse(Course course, Long id) {
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            session.beginTransaction();
            Course existingCourse = getCourseById(id);
            if (existingCourse != null) {
                existingCourse.setName(course.getName());
                session.update(existingCourse);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void addTeacherInCourse(Long courseId, Integer teacherId) {

    }

//
//    @Override
//    public void updateCourse(Course course, Long id) {
//        try (
//                Connection connection = DataBaseConnection.getConnection();
//                PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE)
//        ) {
//            statement.setString(1, course.getName());
//            if (id != null) {
//                statement.setLong(2, id);
//            } else {
//                throw new SQLException("Updating course failed, no ID obtained.");
//            }
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void addTeacherInCourse(Long courseId, Integer teacherId) {
//        try (
//                Connection connection = DataBaseConnection.getConnection();
//                PreparedStatement statement = connection.prepareStatement(ADD_TEACHER_IN_COURSE)
//        ) {
//            statement.setLong(1, courseId);
//            statement.setInt(2, teacherId);
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}

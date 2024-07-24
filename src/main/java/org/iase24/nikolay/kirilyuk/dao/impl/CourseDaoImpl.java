package org.iase24.nikolay.kirilyuk.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iase24.nikolay.kirilyuk.dao.CourseDao;
import org.iase24.nikolay.kirilyuk.dto.CourseDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
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
            courses = session.createQuery(
                            "SELECT c FROM Course c LEFT JOIN FETCH c.students " +
                                    "LEFT JOIN FETCH c.teachers" +
                                    " WHERE c.id = :id"
                            , Course.class)
                    .setParameter("id", id)
                    .uniqueResult();
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
    public void addTeacherToCourse(Long courseId, Long teacherId) {
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            session.beginTransaction();
            Course course = session.get(Course.class, courseId);
            Teacher teacher = session.get(Teacher.class, teacherId);

            if (course != null && teacher != null) {
                course.getTeachers().add(teacher);
                session.saveOrUpdate(course);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}

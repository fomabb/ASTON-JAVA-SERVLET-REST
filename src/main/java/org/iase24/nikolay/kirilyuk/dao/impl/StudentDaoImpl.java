package org.iase24.nikolay.kirilyuk.dao.impl;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iase24.nikolay.kirilyuk.dao.StudentDao;
import org.iase24.nikolay.kirilyuk.dto.StudentDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Course;
import org.iase24.nikolay.kirilyuk.entity.Student;
import org.iase24.nikolay.kirilyuk.util.HibernateUtil;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.util.List;
import java.util.stream.Collectors;

public class StudentDaoImpl implements StudentDao {

    @Override
    public List<StudentDataDTO> getAllStudent() {
        List<StudentDataDTO> studentsDTOs = null;
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            List<Student> students = session.createQuery("from Student").list();
            studentsDTOs = students.stream()
                    .map(student -> new StudentDataDTO(student.getId(), student.getName(), student.getStatus()))
                    .collect(Collectors.toList());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return studentsDTOs;
    }

    @Override
    public void addStudent(Student student) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            student.setStatus(StatusUser.STUDENT);
            session.save(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public void deleteStudent(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Student student = session.load(Student.class, id);
            session.delete(student);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Student getStudentById(Long id) {
        Student student = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            student = session.createQuery(
                            "SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id = :id"
                            , Student.class)
                    .setParameter("id", id)
                    .uniqueResult();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return student;
    }

    @Override
    public void addStudentToCourse(Long studentId, Long courseId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            Student student = session.get(Student.class, studentId);
            Course course = session.get(Course.class, courseId);

            if (student != null && course != null) {
                student.getCourses().add(course);
                course.getStudents().add(student);

                session.saveOrUpdate(student);
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

package org.iase24.nikolay.kirilyuk.dao.impl;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.iase24.nikolay.kirilyuk.dao.TeacherDao;
import org.iase24.nikolay.kirilyuk.dto.TeacherDataDTO;
import org.iase24.nikolay.kirilyuk.entity.Teacher;
import org.iase24.nikolay.kirilyuk.util.HibernateUtil;
import org.iase24.nikolay.kirilyuk.util.enumirate.StatusUser;

import java.util.List;
import java.util.stream.Collectors;

public class TeacherDaoImpl implements TeacherDao {

    @Override
    public List<TeacherDataDTO> getAllTeachers() {
        List<TeacherDataDTO> teachersDTOs = null;
        Transaction transaction = null;

        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            transaction = session.beginTransaction();
            List<Teacher> teachers = session.createQuery("from Teacher").list();
            teachersDTOs = teachers.stream()
                    .map(teacher -> new TeacherDataDTO(teacher.getId(), teacher.getName(), teacher.getStatus()))
                    .collect(Collectors.toList());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return teachersDTOs;
    }

    @Override
    public void addTeacher(Teacher teacher) {
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            session.beginTransaction();
            teacher.setStatus(StatusUser.TEACHER);
            session.save(teacher);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Teacher getTeacherById(Long id) {
        Teacher teacher = null;
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            transaction = session.beginTransaction();
            teacher = session.get(Teacher.class, id);
            Hibernate.initialize(teacher.getStudents());
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return teacher;
    }

    @Override
    public void deleteTeacherById(Long id) {
        Transaction transaction = null;
        try (
                Session session = HibernateUtil.getSessionFactory().openSession()
        ) {
            transaction = session.beginTransaction();
            Teacher teacher = session.get(Teacher.class, id);
            if (teacher != null) {
                session.delete(teacher);
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

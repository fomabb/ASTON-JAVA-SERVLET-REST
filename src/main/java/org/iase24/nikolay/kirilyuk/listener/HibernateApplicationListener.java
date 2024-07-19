package org.iase24.nikolay.kirilyuk.listener;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.iase24.nikolay.kirilyuk.util.HibernateUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class HibernateApplicationListener implements ServletContextListener {

    private static SessionFactory sessionFactory;

    public void contextInitialized(ServletContextEvent sce) {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize Hibernate", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
        HibernateUtil.shutdown();
    }
}

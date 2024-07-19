package org.iase24.nikolay.kirilyuk.util;


import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public class HibernateUtil {

    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Hibernate Exception: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}

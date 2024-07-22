//package org.iase24.nikolay.kirilyuk.listener;
//
//import org.flywaydb.core.Flyway;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//@WebListener
//public class FlywayListener implements ServletContextListener {
//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "postgres";
//    private static final String DRIVER = "org.postgresql.Driver";
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            Flyway flyway = Flyway.configure()
//                    .dataSource(URL, USER, PASSWORD)
//                    .driver(DRIVER)
//                    .load();
//
//            flyway.migrate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to execute database migrations", e);
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//        ServletContextListener.super.contextDestroyed(sce);
//    }
//}

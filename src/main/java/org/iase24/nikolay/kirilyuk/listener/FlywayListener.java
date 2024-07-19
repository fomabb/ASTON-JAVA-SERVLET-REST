//package org.iase24.nikolay.kirilyuk.listener;
//
//import org.iase24.nikolay.kirilyuk.config.DataBaseMigration;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//@WebListener
//public class FlywayListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            ServletContext context = sce.getServletContext();
//            DataBaseMigration.migrate();
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

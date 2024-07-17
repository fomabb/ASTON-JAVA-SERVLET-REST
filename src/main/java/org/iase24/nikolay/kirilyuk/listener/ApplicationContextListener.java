//package org.iase24.nikolay.kirilyuk.listener;
//
//import org.iase24.nikolay.kirilyuk.util.DataBaseMigration;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//import javax.servlet.annotation.WebListener;
//
//@WebListener
//public class ApplicationContextListener implements ServletContextListener {
//
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        try {
//            DataBaseMigration.migrate();
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Failed to execute database migrations", e);
//        }
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent sce) {
//    }
//}

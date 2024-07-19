//package org.iase24.nikolay.kirilyuk.config;
//
//
//import org.flywaydb.core.Flyway;
//
//public class DataBaseMigration {
//
//    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
//    private static final String USER = "postgres";
//    private static final String PASSWORD = "postgres";
//    private static final String DRIVER = "org.postgresql.Driver";
//
//
//    public static void migrate() {
//        Flyway flyway = Flyway.configure()
//                .dataSource(URL, USER, PASSWORD)
//                .driver(DRIVER)
//                .locations("classpath:db/migration")
//                .load();
//
//        flyway.migrate();
//    }
//}
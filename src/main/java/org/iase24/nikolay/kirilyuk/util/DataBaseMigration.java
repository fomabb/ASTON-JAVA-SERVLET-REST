//package org.iase24.nikolay.kirilyuk.util;
//
//import org.flywaydb.core.Flyway;
//
//public class DataBaseMigration {
//
//    public static void migrate() {
//        Flyway flyway = Flyway.configure()
//                .dataSource(
//                        "jdbc:postgresql://localhost:5432/postgres",
//                        "postgres",
//                        "postgres")
//                .load();
//
//        flyway.migrate();
//    }
//}
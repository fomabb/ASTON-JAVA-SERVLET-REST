package org.iase24.nikolay.kirilyuk.config;


import org.flywaydb.core.Flyway;

public class DataBaseMigration {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";


    public static void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(URL, USER, PASSWORD)
                .locations("classpath:db/migration")
                .load();

        flyway.migrate();
    }
}
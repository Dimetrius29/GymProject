package com.itrex.java.lab.service;

import static com.itrex.java.lab.properties.Properties.*;

import org.flywaydb.core.Flyway;

public class FlywayService {

    private Flyway flyway;

    public FlywayService() {
        init();
    }

    public void migrate() {
        flyway.migrate();
    }

    public void clean() {
        flyway.clean();
    }

    private void init() {
        flyway = Flyway.configure()
                .dataSource(H2_URL, H2_USER, H2_PASSWORD)
                .locations(MIGRATIONS_LOCATION)
                .load();
    }
}

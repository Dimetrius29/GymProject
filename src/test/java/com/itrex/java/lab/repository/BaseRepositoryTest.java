package com.itrex.java.lab.repository;

import com.itrex.java.lab.repository.config.TestContextConfiguration;
import org.flywaydb.core.Flyway;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestContextConfiguration.class)
public abstract class BaseRepositoryTest {

    @Autowired
    private Flyway flyway;

    @Before
    public void initDB() {
        flyway.migrate();
    }

    @After
    public void cleanDB() {
        flyway.clean();
    }
}
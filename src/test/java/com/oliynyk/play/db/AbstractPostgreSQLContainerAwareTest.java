package com.oliynyk.play.db;

import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractPostgreSQLContainerAwareTest {
    protected static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = new PostgreSQLContainer();

    static {
        POSTGRE_SQL_CONTAINER.start();
        System.setProperty("spring.datasource.url", POSTGRE_SQL_CONTAINER.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRE_SQL_CONTAINER.getUsername());
        System.setProperty("spring.datasource.password", POSTGRE_SQL_CONTAINER.getPassword());
    }

}

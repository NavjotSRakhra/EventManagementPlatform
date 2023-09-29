/*
 * Copyright (c) 2023 Navjot Singh Rakhra. All rights reserved.
 */

package io.github.navjotsrakhra.eventmanager.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * The DataSourceConfig class is responsible for configuring the data source for the application.
 */
@Configuration
public class DataSourceConfig {
    /**
     * Configure the data source for the application.
     *
     * @return A DataSource for the application.
     */
    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("org.postgresql.Driver");
//        dataSourceBuilder.url("jdbc:postgresql://dpg-cjcbi9k5kgrc73aacc60-a.singapore-postgres.render.com/posts_and_users");
//        dataSourceBuilder.username("posts_and_users_user");
//        dataSourceBuilder.password(System.getenv("pass_db"));

        dataSourceBuilder.url("jdbc:postgresql://localhost:5432/posts_and_users");
        dataSourceBuilder.username("test");
        dataSourceBuilder.password("test");
        return dataSourceBuilder.build();
    }
}

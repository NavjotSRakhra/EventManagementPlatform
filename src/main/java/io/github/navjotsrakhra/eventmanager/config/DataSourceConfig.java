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
        dataSourceBuilder.url(System.getenv("url_db"));
        dataSourceBuilder.username(System.getenv("username_db"));
        dataSourceBuilder.password(System.getenv("pass_db"));
        return dataSourceBuilder.build();
    }
}

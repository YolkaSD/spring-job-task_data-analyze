package com.example.dataanalyze.configurations;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

/**
 * Конфигурационный класс для настройки и создания DataSource и JdbcTemplate
 * для работы с базой данных, используя HikariCP в качестве пула соединений.
 * Использует настройки, определенные в файле application.properties, с префиксом "db".
 */
@Configuration
@PropertySource(value = {"classpath:application.properties"}, ignoreResourceNotFound = true, encoding = "UTF-8")
public class DbConfig {

    @Bean
    @ConfigurationProperties(prefix = "db")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}

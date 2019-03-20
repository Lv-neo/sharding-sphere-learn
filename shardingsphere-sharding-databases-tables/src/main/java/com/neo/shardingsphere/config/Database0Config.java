package com.neo.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Data
@ConfigurationProperties(prefix = "sharding.jdbc.datasource.ds0")
@Component
public class Database0Config {
    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;
    private String databaseName;

    public DruidDataSource createDataSource() {
        DruidDataSource result = new DruidDataSource();
        result.setDriverClassName(getDriverClassName());
        result.setUrl(getJdbcUrl());
        result.setUsername(getUsername());
        result.setPassword(getPassword());
        return result;
    }
}

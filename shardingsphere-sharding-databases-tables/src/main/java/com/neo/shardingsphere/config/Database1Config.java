package com.neo.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

@Data
@ConfigurationProperties(prefix = "sharding.jdbc.datasource.ds1")
@Component
public class Database1Config {
    private String jdbcUrl;
    private String username;
    private String password;
    private String driverClassName;
    private String databaseName;

    public DruidDataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(getDriverClassName());
        dataSource.setUrl(getJdbcUrl());
        dataSource.setUsername(getUsername());
        dataSource.setPassword(getPassword());
        return dataSource;
    }


}

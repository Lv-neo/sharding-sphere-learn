package com.neo.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import io.shardingjdbc.core.api.MasterSlaveDataSourceFactory;
import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

@Configuration
//@EnableConfigurationProperties(ShardingMasterSlaveConfig.class)
//@ConditionalOnProperty({"sharding.jdbc.datasource.ds-master.jdbc-url", "sharding.jdbc.masterslave.master-data-source-name"})
public class ShardingDataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(ShardingDataSourceConfig.class);

//    @Autowired(required = false)
//    private ShardingMasterSlaveConfig shardingMasterSlaveConfig;

    @Autowired
    Database1Config database1Config;

    @Autowired
    Database0Config database0Config;

//    @Bean("dataSource")
//    public DataSource masterSlaveDataSource() throws SQLException {
//        shardingMasterSlaveConfig.getDataSources().forEach((k, v) -> configDataSource(v));
//        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
//        dataSourceMap.putAll(shardingMasterSlaveConfig.getDataSources());
//
//        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, shardingMasterSlaveConfig.getMasterSlaveRule(), Maps.newHashMap());
//        log.info("masterSlaveDataSource config complete");
//        return dataSource;
//    }

    @Bean("dataSource")
    public DataSource masterSlaveDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
        dataSourceMap.put(database0Config.getDatabaseName(),configDataSource(database0Config.createDataSource()));
        dataSourceMap.put(database1Config.getDatabaseName(),configDataSource(database1Config.createDataSource()));

        MasterSlaveRuleConfiguration masterSlaveRule = new MasterSlaveRuleConfiguration();

        masterSlaveRule.setName("ds_master_slave");
        masterSlaveRule.setMasterDataSourceName(database0Config.getDatabaseName());
        masterSlaveRule.setSlaveDataSourceNames(Arrays.asList(database1Config.getDatabaseName()));
        masterSlaveRule.setLoadBalanceAlgorithmType(MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN);

        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRule, Maps.newHashMap());
        log.info("masterSlaveDataSource config complete");
        return dataSource;
    }

    private DataSource configDataSource(DruidDataSource druidDataSource) {
        druidDataSource.setMaxActive(20);
        druidDataSource.setInitialSize(1);
        druidDataSource.setMaxWait(60000);
        druidDataSource.setMinIdle(1);
        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
        druidDataSource.setMinEvictableIdleTimeMillis(300000);
        druidDataSource.setValidationQuery("select 'x'");
        druidDataSource.setTestWhileIdle(true);
        druidDataSource.setTestOnBorrow(false);
        druidDataSource.setTestOnReturn(false);
        druidDataSource.setPoolPreparedStatements(true);
        druidDataSource.setMaxOpenPreparedStatements(20);
        druidDataSource.setUseGlobalDataSourceStat(true);
        try {
            druidDataSource.setFilters("stat,wall,slf4j");
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        return druidDataSource;
    }

}

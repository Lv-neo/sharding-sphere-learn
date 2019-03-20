package com.neo.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import io.shardingjdbc.core.api.ShardingDataSourceFactory;
import io.shardingjdbc.core.api.config.ShardingRuleConfiguration;
import io.shardingjdbc.core.api.config.TableRuleConfiguration;
import io.shardingjdbc.core.api.config.strategy.InlineShardingStrategyConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ShardingDataSourceConfig {

    private static final Logger log = LoggerFactory.getLogger(ShardingDataSourceConfig.class);

    @Autowired
    Database1Config database1Config;

    @Autowired
    Database0Config database0Config;

    @Bean("dataSource")
    public DataSource shardingDataSource() throws SQLException {
        Map<String, DataSource> dataSourceMap = Maps.newHashMap();
//        dataSourceMap.put(database0Config.getDatabaseName(),configDataSource(database0Config.createDataSource()));
//        dataSourceMap.put(database1Config.getDatabaseName(),configDataSource(database1Config.createDataSource()));

        dataSourceMap.put(database0Config.getDatabaseName(),database0Config.createDataSource());
        dataSourceMap.put(database1Config.getDatabaseName(),database1Config.createDataSource());
        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration();
        orderTableRuleConfig.setLogicTable("t_order");
        orderTableRuleConfig.setActualDataNodes("ds-${0..1}.t_order${0..1}");

        // 配置分库 + 分表策略
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(new InlineShardingStrategyConfiguration("user_id", "ds-${id % 2}"));
        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("order_id", "t_order${order_id % 2}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
        shardingRuleConfig.set

        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new ConcurrentHashMap(), new Properties());
        log.info("ShardingDataSource config complete");
        return dataSource;
    }

//    private DataSource configDataSource(DruidDataSource druidDataSource) {
//        druidDataSource.setMaxActive(20);
//        druidDataSource.setInitialSize(1);
//        druidDataSource.setMaxWait(60000);
//        druidDataSource.setMinIdle(1);
//        druidDataSource.setTimeBetweenEvictionRunsMillis(60000);
//        druidDataSource.setMinEvictableIdleTimeMillis(300000);
//        druidDataSource.setValidationQuery("select 'x'");
//        druidDataSource.setTestWhileIdle(true);
//        druidDataSource.setTestOnBorrow(false);
//        druidDataSource.setTestOnReturn(false);
//        druidDataSource.setPoolPreparedStatements(true);
//        druidDataSource.setMaxOpenPreparedStatements(20);
//        druidDataSource.setUseGlobalDataSourceStat(true);
//        try {
//            druidDataSource.setFilters("stat,wall,slf4j");
//        } catch (SQLException e) {
//            log.error("druid configuration initialization filter", e);
//        }
//        return druidDataSource;
//    }

}

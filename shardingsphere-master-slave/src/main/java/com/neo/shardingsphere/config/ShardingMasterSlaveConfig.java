package com.neo.shardingsphere.config;

import com.alibaba.druid.pool.DruidDataSource;
import io.shardingjdbc.core.api.algorithm.masterslave.MasterSlaveLoadBalanceAlgorithmType;
import io.shardingjdbc.core.api.config.MasterSlaveRuleConfiguration;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "sharding.jdbc")
public class ShardingMasterSlaveConfig {

    private Map<String, DruidDataSource> dataSources = new HashMap<>();
//    private Map<String, HikariDataSource> dataSources = new HashMap<>();

    private MasterSlaveRuleConfiguration masterSlaveRule = new MasterSlaveRuleConfiguration();

    @Autowired
    private Environment env;

    public Map<String, DruidDataSource> getDataSources() {
        dataSources = new HashMap<>();

        // 配置主库
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName(env.getProperty("sharding.jdbc.datasource.ds-master.driver-class-name"));
        masterDataSource.setUrl(env.getProperty("sharding.jdbc.datasource.ds-master.jdbc-url"));
        masterDataSource.setUsername(env.getProperty("sharding.jdbc.datasource.ds-master.username"));
        masterDataSource.setPassword(env.getProperty("sharding.jdbc.datasource.ds-master.password"));
        dataSources.put("ds-master", masterDataSource);

        // 配置第一个从库
        DruidDataSource slaveDataSource1 = new DruidDataSource();
        slaveDataSource1.setDriverClassName(env.getProperty("sharding.jdbc.datasource.ds-slave-0.driver-class-name"));
        slaveDataSource1.setUrl(env.getProperty("sharding.jdbc.datasource.ds-slave-0.jdbc-url"));
        slaveDataSource1.setUsername(env.getProperty("sharding.jdbc.datasource.ds-slave-0.username"));
        slaveDataSource1.setPassword(env.getProperty("sharding.jdbc.datasource.ds-slave-0.password"));
        dataSources.put("ds-slave-0", slaveDataSource1);
        return dataSources;
    }

    public MasterSlaveRuleConfiguration getMasterSlaveRule() {
        masterSlaveRule.setName("ds_master_slave");
        masterSlaveRule.setMasterDataSourceName("ds-master");
        masterSlaveRule.setSlaveDataSourceNames(Arrays.asList("ds-slave-0"));
        masterSlaveRule.setLoadBalanceAlgorithmType(MasterSlaveLoadBalanceAlgorithmType.ROUND_ROBIN);
        return masterSlaveRule;
    }
}

package com.portal.config;

import com.portal.common.datasource.DynamicDataSource;
import com.portal.common.datasource.DynamicDataSourceTransactionManager;
import com.portal.common.mybatis.SqlSessionFactoryBean;
import com.portal.common.utils.exception.SystemException;
import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * 数据源配置
 *
 * @author dcp
 */
@Configuration
public class DatasourceConfig {

    /**
     * Write datasource source druid datasource source.
     *
     * @return the druid datasource source
     */
    @Primary
    @Bean(name = "writeDataSource")
    @ConfigurationProperties("spring.datasource.write")
    public DruidDataSource writeDataSource() {
        return new DruidDataSource();
    }

    /**
     * Read datasource source druid datasource source.
     *
     * @return the druid datasource source
     */
    @Bean(name = "readDataSource")
    @ConfigurationProperties("spring.datasource.read")
    public DruidDataSource readDataSource() {
        return new DruidDataSource();
    }

    /**
     * Dynamic datasource source datasource source.
     *
     * @return the datasource source
     */
    @Bean(name = "dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setWriteDataSource(writeDataSource());
        dynamicDataSource.setReadDataSource(readDataSource());

        return dynamicDataSource;
    }

    /**
     * Dynamic transaction manager datasource source transaction manager.
     *
     * @param dynamicDataSource the dynamic datasource source
     * @return the datasource source transaction manager
     */
    @Bean(name = "dynamicTransactionManager")
    public DataSourceTransactionManager dynamicTransactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DynamicDataSourceTransactionManager(dynamicDataSource);
    }

    /**
     * Dynamic sql session factory sql session factory.
     *
     * @param dynamicDataSource the dynamic datasource source
     * @param properties        the properties
     * @return the sql session factory
     */
    @Bean
    @ConfigurationProperties(prefix = MybatisProperties.MYBATIS_PREFIX)
    public SqlSessionFactory dynamicSqlSessionFactory(
        @Qualifier("dynamicDataSource") DataSource dynamicDataSource,
        MybatisProperties properties) {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDataSource);
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(properties.getConfigLocation()));
        sessionFactory.setMapperLocations(properties.resolveMapperLocations());
        try {
            return sessionFactory.getObject();
        } catch (Exception e) {
            throw new SystemException(e);
        }
    }

}

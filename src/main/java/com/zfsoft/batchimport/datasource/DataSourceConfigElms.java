package com.zfsoft.batchimport.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author chenyq
 * @Description: 电子证照主数据库
 * @date 2020/6/23 10:03
 */

@Configuration
@MapperScan(basePackages = "com.zfsoft.batchimport.mapper.elms", sqlSessionFactoryRef = "elmsSqlSessionFactory")
public class DataSourceConfigElms {

    // 将这个对象放入Spring容器中
   /* @Bean(name = "elmsDataSource")
    // 表示这个数据源是默认数据源
    @Primary
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.druidelms")
    public DataSource getMiddleDateSource()
    {
        return DataSourceBuilder.create().build();
    }
*/
    @Primary
    @Bean(name = "elmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druidelms")
    public DruidDataSource getElmsDateSource() {
        return new DruidDataSource();
    }


    @Bean(name = "elmsSqlSessionFactory")
    // 表示这个数据源是默认数据源
    @Primary
    // @Qualifier表示查找Spring容器中名字为elmsDataSource的对象
    public SqlSessionFactory elmsSqlSessionFactory(@Qualifier("elmsDataSource") DataSource datasource)
            throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/mysql/elms/*.xml"));
        return bean.getObject();
    }

    @Bean("elmsSqlSessionTemplate")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate elmsSqlSessionTemplate(
            @Qualifier("elmsSqlSessionFactory") SqlSessionFactory sessionFactory)
    {
        return new SqlSessionTemplate(sessionFactory);
    }



    @Bean
    JdbcTemplate masterJdbcTemplate(@Qualifier("elmsDataSource") DataSource elmsDataSource){
        return new JdbcTemplate(elmsDataSource);
    }


    // 事务管理器
    @Bean
    public PlatformTransactionManager masterTransactionManager(@Qualifier("elmsDataSource")DataSource masterDataSource) {
        return new DataSourceTransactionManager(masterDataSource);
    }
}


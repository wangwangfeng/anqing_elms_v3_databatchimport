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
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author chenyq
 * @Description: 电子证照中间数据库
 * @date 2020/6/23 10:04
 */

@Configuration
@MapperScan(basePackages = "com.zfsoft.batchimport.mapper.middle", sqlSessionFactoryRef = "middleSqlSessionFactory")
public class DataSourceConfigMiddle {


    // 将这个对象放入Spring容器中
    /*  @Bean(name = "middleDataSource")
    // 表示这个数据源是默认数据源
  @Primary
    // 读取application.properties中的配置参数映射成为一个对象
    // prefix表示参数的前缀
    @ConfigurationProperties(prefix = "spring.datasource.druidmiddle")
    public DataSource getMiddleDateSource()
    {
        return DataSourceBuilder.create().build();
    }
*/
    @Primary
    @Bean(name = "middleDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druidmiddle")
    public DruidDataSource getMiddleDateSource() {
        return new DruidDataSource();
    }


    @Bean(name = "middleSqlSessionFactory")
    // 表示这个数据源是默认数据源
    @Primary
    // @Qualifier表示查找Spring容器中名字为middleDataSource的对象
    public SqlSessionFactory middleSqlSessionFactory(@Qualifier("middleDataSource") DataSource datasource)
            throws Exception
    {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(datasource);
        bean.setMapperLocations(
                // 设置mybatis的xml所在位置
                new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/mysql/middle/*.xml"));
        return bean.getObject();
    }

    @Bean("middleSqlSessionTemplate")
    // 表示这个数据源是默认数据源
    @Primary
    public SqlSessionTemplate middleSqlSessionTemplate(
            @Qualifier("middleSqlSessionFactory") SqlSessionFactory sessionFactory)
    {
        return new SqlSessionTemplate(sessionFactory);
    }
}

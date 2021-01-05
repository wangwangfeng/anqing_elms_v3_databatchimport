package com.zfsoft.batchimport;

import com.zfsoft.batchimport.factory.YamlPropertyLoaderFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableScheduling // 开启对定时任务支持
@EnableAsync//开启异步线程池操作
@SpringBootApplication(scanBasePackages={"com.zfsoft"}
,exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement  // 事务
@PropertySource(value = {"classpath:prop/dev/application.yml", "classpath:generator/config.properties"}, factory = YamlPropertyLoaderFactory.class)
//@PropertySource(value = {"classpath:prop/test/application.yml", "classpath:generator/config.properties"}, factory = YamlPropertyLoaderFactory.class)
//@PropertySource(value = {"classpath:prop/prod/application.yml", "classpath:generator/config.properties"}, factory = YamlPropertyLoaderFactory.class)
//@MapperScan({"com.zfsoft.batchimport.middle.mapper.MiddleMapper"} )
public class WorkflowAsynHandleApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkflowAsynHandleApplication.class, args);
    }

}

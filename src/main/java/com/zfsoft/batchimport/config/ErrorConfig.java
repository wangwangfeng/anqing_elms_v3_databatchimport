package com.zfsoft.batchimport.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @Auther: kkfan
 * @Date: 2019/6/13 16:57
 * @Description:
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {

        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");

        registry.addErrorPages(error404Page);
    }

}

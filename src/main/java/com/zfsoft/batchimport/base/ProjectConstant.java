package com.zfsoft.batchimport.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * 项目常量
 */
@Configuration
@Order(1)
public class ProjectConstant {

    public static String basePackage;//生成代码所在的基础包名称，可根据自己的项目修改（注意：这个配置修改之后需要手工修改src目录项目默认的包路径，使其保持一致，不然会找不到类）

    public static String modelPackage;//生成的Model所在包

    public static String mapperPackage;//生成的Mapper所在包

    public static String servicePackage;//生成的Service所在包

    public static String serviceImplPackage;//生成的ServiceImpl所在包

    public static String controllerPackage;//生成的Controller所在包

    @Value("${project.base.package}")
    public void setBasePackage(String basePackage) {
        ProjectConstant.basePackage = basePackage == null ? null : basePackage.trim();
    }

    @Value("${project.entity.package}")
    public void setModelPackage(String modelPackage) {
        ProjectConstant.modelPackage = modelPackage == null ? null : modelPackage.trim();
    }

    @Value("${project.mapper.package}")
    public void setMapperPackage(String mapperPackage) {
        ProjectConstant.mapperPackage = mapperPackage == null ? null : mapperPackage.trim();
    }

    @Value("${project.service.package}")
    public void setServicePackage(String servicePackage) {
        ProjectConstant.servicePackage = servicePackage == null ? null : servicePackage.trim();
    }

    @Value("${project.service.impl.package}")
    public void setServiceImplPackage(String serviceImplPackage) {
        ProjectConstant.serviceImplPackage = serviceImplPackage == null ? null : serviceImplPackage.trim();
    }

    @Value("${project.controller.package}")
    public void setControllerPackage(String controllerPackage) {
        ProjectConstant.controllerPackage = controllerPackage == null ? null : controllerPackage.trim();
    }

    public String getBasePackage() {
        return basePackage;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    @Override
    public String toString() {
        return "ProjectConstant{" +
                "basePackage='" + basePackage + '\'' +
                ", modelPackage='" + modelPackage + '\'' +
                ", mapperPackage='" + mapperPackage + '\'' +
                ", servicePackage='" + servicePackage + '\'' +
                ", serviceImplPackage='" + serviceImplPackage + '\'' +
                ", controllerPackage='" + controllerPackage + '\'' +
                '}';
    }
}

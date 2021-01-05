package com.zfsoft.batchimport.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanUtil implements ApplicationContextAware {
	private static ApplicationContext appContext;

	@Override
	public void setApplicationContext(ApplicationContext appContext) throws BeansException {
		SpringBeanUtil.appContext = appContext;
	}

	public static ApplicationContext getAppContext() {
		return SpringBeanUtil.appContext;
	}
	public static Object getBean(String name) {
        return appContext.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return appContext.getBean(name, requiredType);
    }

    public static boolean containsBean(String name) {
        return appContext.containsBean(name);
    }

    public static boolean isSingleton(String name) {
        return appContext.isSingleton(name);
    }

    public static Class<? extends Object> getType(String name) {
        return appContext.getType(name);
    }

}

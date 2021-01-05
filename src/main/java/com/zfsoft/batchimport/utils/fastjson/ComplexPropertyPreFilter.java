package com.zfsoft.batchimport.utils.fastjson;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.formula.functions.T;

import com.alibaba.fastjson.serializer.PropertyFilter;

/**
 * 
 * @Description:jastjson 对象属性转换配置
 * @author:zhujiajian
 * @date 2017年9月12日 下午4:57:44 
 * @copyright 版权由上海卓繁信息技术股份有限公司拥有
 */
public class ComplexPropertyPreFilter implements PropertyFilter {  
    private Map<Class<?>, Set<String>> includeMap = new HashMap<Class<?>, Set<String>>();  
    @Override  
    public boolean apply(Object source, String name, Object value) {  
        for(Entry<Class<?>, Set<String>> entry : includeMap.entrySet()) {  
            Class<?> class1 = entry.getKey();  
            if(source.getClass() == class1){  
                Set<String> fields = entry.getValue();  
                for(String field : fields) {  
                    if(field.equals(name)){  
                        return true;  
                    }  
                }  
            }  
        }  
        return false;  
    }  
      
    public ComplexPropertyPreFilter(Map<Class<?>, Set<String>> includeMap){  
        this.includeMap = includeMap;  
    }  
    
    @SuppressWarnings("unchecked")
	public ComplexPropertyPreFilter(Object[]... args){  
        for(Object[] arg : args){
        	 Set<String> set = new HashSet<String>();  
             Class<T> c = null;
             for(int i=0;i<arg.length;i++){
            	 if(i == 0){
            		 c = (Class<T>)arg[i];
            	 }
            	 set.add(arg[i].toString());
             }
             includeMap.put(c, set);   
        }
        	
    } 
}  




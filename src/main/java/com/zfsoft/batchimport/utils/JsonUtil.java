package com.zfsoft.batchimport.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zfsoft.batchimport.utils.fastjson.ComplexPropertyPreFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;

/**
 * json帮助类
 * 
 * @author gaolh
 * @date 2016-3-24
 * 
 */
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * 将数组转换成JSON
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param object
	 * @return
	 */
	public static String arrayToJson(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将JSON转换成数组,其中valueClz为数组中存放的对象的Class
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * 
	 * @param json
	 *            转化为数组的json对象
	 * @param valueClz
	 *            数组中存放的对象的Class
	 * @return
	 */
	public static Object jsonToArray(String json, Class valueClz) {
		JSONArray jsonArray = JSONArray.fromObject(json);
		return JSONArray.toArray(jsonArray, valueClz);
	}

	/**
	 * 将Collection转换成JSON
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param object
	 *            需要转化为json的Collection对象
	 * @return
	 */
	public static String collectionToJson(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);
		return jsonArray.toString();
	}

	/**
	 * 将Map转换成JSON
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param object
	 *            需要转化为json的map对象
	 * @return
	 */
	public static String mapToJson(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成Map,其中valueClz为Map中value的Class,keyArray为Map的key
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * 
	 * @param keyArray
	 *            Map的key
	 * @param json
	 *            需要转化为map的json字符串
	 * @param valueClz
	 *            Map中value的Class
	 * @return
	 */
	public static Map json2Map(Object[] keyArray, String json, Class valueClz) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		Map classMap = new HashMap();

		for (int i = 0; i < keyArray.length; i++) {
			classMap.put(keyArray[i], valueClz);
		}

		return (Map) JSONObject.toBean(jsonObject, Map.class, classMap);
	}

	/**
	 * 将POJO转换成JSON
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param object
	 * @return
	 */
	public static String beanToJson(Object object) {
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject.toString();
	}

	/**
	 * 将JSON转换成POJO,其中beanClz为POJO的Class
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param json
	 *            转化为pojo的json字符串
	 * @param beanClz
	 *            POJO的Class
	 * @return
	 */
	public static Object jsonToObject(String json, Class beanClz) {
		return JSONObject.toBean(JSONObject.fromObject(json), beanClz);
	}

	/**
	 * 将json字符串转化为list对象
	 * 
	 * @param json
	 *            json字符串
	 * @param beanClz
	 *            list中的对象
	 */
	public static List jsonToCollection(String json, Class beanClz) {
		return (List) JSONArray.toList(JSONArray.fromObject(json), beanClz);
	}


	/**
	 * json转换为java对象
	 * 
	 * <pre>
	 * return JackJson.fromJsonToObject(this.answersJson, JackJson.class);
	 * </pre>
	 * 
	 * @param <T>
	 *            要转换的对象
	 * @param json
	 *            字符串
	 * @param valueType
	 *            对象的class
	 * @return 返回对象
	 */
	public static <T> T fromJsonToObject(String json, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, valueType);		} catch (JsonParseException e) {
			logger.error("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	// 将String转换成JSON
	/**
	 * 根据key、value生成json对象
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param key
	 *            json的key值
	 * @param value
	 *            json的value值
	 * @return
	 */
	public static String stringToJson(String key, String value) {
		JSONObject object = new JSONObject();
		object.put(key, value);
		return object.toString();
	}

	/**
	 * 获取json中某个key的值
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * @param json
	 *            json字符串
	 * @param key
	 *            需要获取的key值
	 * @return
	 */
	public static String jsonToString(String json, String key) {
		JSONObject jsonObject = JSONObject.fromObject(json);
		return jsonObject.get(key).toString();
	}

	/***
	 * 将List对象序列化为JSON文本
	 */
	public static <T> String toJSONString(List<T> list) {
		JSONArray jsonArray = JSONArray.fromObject(list);

		return jsonArray.toString();
	}

	/***
	 * 将对象序列化为JSON文本
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		JSONArray jsonArray = JSONArray.fromObject(object);

		return jsonArray.toString();
	}

	/***
	 * 将JSON对象数组序列化为JSON文本
	 * 
	 * @param jsonArray
	 * @return
	 */
	public static String toJSONString(JSONArray jsonArray) {
		return jsonArray.toString();
	}

	/***
	 * 将JSON对象序列化为JSON文本
	 * 
	 * @param jsonObject
	 * @return
	 */
	public static String toJSONString(JSONObject jsonObject) {
		return jsonObject.toString();
	}

	/***
	 * 将对象转换为List对象
	 * 
	 * @param object
	 * @return
	 */
	public static List toArrayList(Object object) {
		List arrayList = new ArrayList();

		JSONArray jsonArray = JSONArray.fromObject(object);

		Iterator it = jsonArray.iterator();
		while (it.hasNext()) {
			JSONObject jsonObject = (JSONObject) it.next();

			Iterator keys = jsonObject.keys();
			while (keys.hasNext()) {
				Object key = keys.next();
				Object value = jsonObject.get(key);
				arrayList.add(value);
			}
		}

		return arrayList;
	}

	/***
	 * 将对象转换为JSON对象数组
	 * 
	 * @param object
	 * @return
	 */
	public static JSONArray toJSONArray(Object object) {
		return JSONArray.fromObject(object);
	}

	/***
	 * 将对象转换为JSON对象
	 * 
	 * @param object
	 * @return
	 */
	public static JSONObject toJSONObject(Object object) {
		return JSONObject.fromObject(object);
	}

	/***
	 * 将对象转换为HashMap
	 * 
	 * @param object
	 * @return
	 */
	public static HashMap toHashMap(Object object) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = JsonUtil.toJSONObject(object);
		Iterator it = jsonObject.keys();
		while (it.hasNext()) {
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}

		return data;
	}

	/***
	 * 将对象转换为List<Map<String,Object>>
	 * 
	 * @param object
	 * @return
	 */
	public static List<Map<String, Object>> toList(Object object) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JSONArray jsonArray = JSONArray.fromObject(object);
		for (Object obj : jsonArray) {
			JSONObject jsonObject = (JSONObject) obj;
			Map<String, Object> map = new HashMap<String, Object>();
			Iterator it = jsonObject.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				Object value = jsonObject.get(key);
				map.put((String) key, value);
			}
			list.add(map);
		}
		return list;
	}

	/***
	 * 将JSON对象数组转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(JSONArray jsonArray, Class<T> objectClass) {
		return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	 * 将对象转换为传入类型的List
	 * 
	 * @param <T>
	 * @param jsonArray
	 * @param objectClass
	 * @return
	 */
	public static <T> List<T> toList(Object object, Class<T> objectClass) {
		JSONArray jsonArray = JSONArray.fromObject(object);

		return JSONArray.toList(jsonArray, objectClass);
	}

	/***
	 * 将JSON对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param jsonObject
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(JSONObject jsonObject, Class<T> beanClass) {
		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/***
	 * 将将对象转换为传入类型的对象
	 * 
	 * @param <T>
	 * @param object
	 * @param beanClass
	 * @return
	 */
	public static <T> T toBean(Object object, Class<T> beanClass) {
		JSONObject jsonObject = JSONObject.fromObject(object);

		return (T) JSONObject.toBean(jsonObject, beanClass);
	}

	/**
	 * 将对象转化为json对象，去除不需要的属性
	 * 
	 * @param obj
	 *            需要转化的对象
	 * @param filterNames
	 *            不需要转化的属性
	 */
	public static JSONObject parseObj(Object obj, String[] filterNames) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT); // 防止自包含

		if (filterNames != null) {
			// 这里是核心，过滤掉不想使用的属性
			jsonConfig.setExcludes(filterNames);
		}
		JSONObject jsonObj = JSONObject.fromObject(obj, jsonConfig);
		return jsonObj;

	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            泛型T 代表主实体类型
	 * @param <D>
	 *            泛型D 代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName
	 *            从实体类在主实体类中的属性名称
	 * @param detailClass
	 *            从实体类型
	 * @return
	 */
	public static <T, D> T toBean(String jsonString, Class<T> mainClass, String detailName, Class<D> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray = (JSONArray) jsonObject.get(detailName);

		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		List<D> detailList = JsonUtil.toList(jsonArray, detailClass);

		try {
			BeanUtils.setProperty(mainEntity, detailName, detailList);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>泛型T
	 *            代表主实体类型
	 * @param <D1>泛型D1
	 *            代表从实体类型
	 * @param <D2>泛型D2
	 *            代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @return
	 */
	public static <T, D1, D2> T toBean(String jsonString, Class<T> mainClass, String detailName1,
			Class<D1> detailClass1, String detailName2, Class<D2> detailClass2) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);

		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JsonUtil.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);

		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>泛型T
	 *            代表主实体类型
	 * @param <D1>泛型D1
	 *            代表从实体类型
	 * @param <D2>泛型D2
	 *            代表从实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailName1
	 *            从实体类在主实体类中的属性
	 * @param detailClass1
	 *            从实体类型
	 * @param detailName2
	 *            从实体类在主实体类中的属性
	 * @param detailClass2
	 *            从实体类型
	 * @param detailName3
	 *            从实体类在主实体类中的属性
	 * @param detailClass3
	 *            从实体类型
	 * @return
	 */
	public static <T, D1, D2, D3> T toBean(String jsonString, Class<T> mainClass, String detailName1,
			Class<D1> detailClass1, String detailName2, Class<D2> detailClass2, String detailName3,
			Class<D3> detailClass3) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		JSONArray jsonArray1 = (JSONArray) jsonObject.get(detailName1);
		JSONArray jsonArray2 = (JSONArray) jsonObject.get(detailName2);
		JSONArray jsonArray3 = (JSONArray) jsonObject.get(detailName3);

		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		List<D1> detailList1 = JsonUtil.toList(jsonArray1, detailClass1);
		List<D2> detailList2 = JsonUtil.toList(jsonArray2, detailClass2);
		List<D3> detailList3 = JsonUtil.toList(jsonArray3, detailClass3);

		try {
			BeanUtils.setProperty(mainEntity, detailName1, detailList1);
			BeanUtils.setProperty(mainEntity, detailName2, detailList2);
			BeanUtils.setProperty(mainEntity, detailName3, detailList3);
		} catch (Exception ex) {
			throw new RuntimeException("主从关系JSON反序列化实体失败！");
		}

		return mainEntity;
	}

	/***
	 * 将JSON文本反序列化为主从关系的实体
	 * 
	 * @param <T>
	 *            主实体类型
	 * @param jsonString
	 *            JSON文本
	 * @param mainClass
	 *            主实体类型
	 * @param detailClass
	 *            存放了多个从实体在主实体中属性名称和类型
	 * @return
	 */
	public static <T> T toBean(String jsonString, Class<T> mainClass, HashMap<String, Class> detailClass) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		T mainEntity = JsonUtil.toBean(jsonObject, mainClass);
		for (Object key : detailClass.keySet()) {
			try {
				Class value = (Class) detailClass.get(key);
				BeanUtils.setProperty(mainEntity, key.toString(), value);
			} catch (Exception ex) {
				throw new RuntimeException("主从关系JSON反序列化实体失败！");
			}
		}
		return mainEntity;
	}

	/**
	 * list转化为json对象
	 * 
	 * @author gaolh
	 * @date 2016-3-24
	 * 
	 * @param fields
	 *            需要转化的list中对象的字段集合
	 * @param total
	 *            集合总数
	 * @param list
	 *            集合对象
	 * @return
	 * @throws Exception
	 */
	public static String listToJson(String[] fields, int total, List list) throws Exception {
		Object[] values = new Object[fields.length];
		String jsonTemp = "{\"total\":" + total + ",\"rows\":[";
		for (int j = 0; j < list.size(); j++) {
			jsonTemp = jsonTemp + "{\"state\":\"closed\",";
			for (int i = 0; i < fields.length; i++) {
				String fieldName = fields[i].toString();
				values[i] = fieldNametoValues(fieldName, list.get(j));
				jsonTemp = jsonTemp + "\"" + fieldName + "\"" + ":\"" + values[i] + "\"";
				if (i != fields.length - 1) {
					jsonTemp = jsonTemp + ",";
				}
			}
			if (j != list.size() - 1) {
				jsonTemp = jsonTemp + "},";
			} else {
				jsonTemp = jsonTemp + "}";
			}
		}
		jsonTemp = jsonTemp + "]}";
		return jsonTemp;
	}

	/**
	 * 
	 * 获取对象内对应字段的值
	 * 
	 * @param fields
	 */
	public static Object fieldNametoValues(String FiledName, Object o) {
		Object value = "";
		String fieldName = "";
		String childFieldName = null;
		ReflectHelper reflectHelper = new ReflectHelper(o);
		if (FiledName.indexOf("_") == -1) {
			if (FiledName.indexOf(".") == -1) {
				fieldName = FiledName;
			} else {
				fieldName = FiledName.substring(0, FiledName.indexOf("."));// 外键字段引用名
				childFieldName = FiledName.substring(FiledName.indexOf(".") + 1);// 外键字段名
			}
		} else {
			fieldName = FiledName.substring(0, FiledName.indexOf("_"));// 外键字段引用名
			childFieldName = FiledName.substring(FiledName.indexOf("_") + 1);// 外键字段名
		}
		value = reflectHelper.getMethodValue(fieldName) == null ? "" : reflectHelper.getMethodValue(fieldName);
		if (value != "" && value != null && (FiledName.indexOf("_") != -1 || FiledName.indexOf(".") != -1)) {
			if (value instanceof List) {
				Object tempValue = "";
				for (Object listValue : (List) value) {
					tempValue = tempValue.toString() + fieldNametoValues(childFieldName, listValue) + ",";
				}
				value = tempValue;
			} else {
				value = fieldNametoValues(childFieldName, value);
			}
		}
		if (value != "" && value != null) {
			value = converUnicode(value.toString());
		}
		return value;
	}

	static Object converUnicode(String jsonValue) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < jsonValue.length(); i++) {
			char c = jsonValue.charAt(i);
			switch (c) {
			case '\"':
				sb.append("\\\"");
				break;
			case '\'':
				sb.append("\\\'");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '/':
				sb.append("\\/");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			default:
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 将对象转换为JSON对象 设置JSON-LIB让其过滤掉引起循环的字段
	 * 如果字段为空则设置JSON-LIB的setCycleDetectionStrategy属性让其自己处理循环
	 * 
	 * @author cbc
	 * @date 2017年1月17日
	 * @param object
	 *            对象
	 * @param properties
	 *            对象忽略属性
	 * @return
	 */
	public static JSONObject toJSONObject(Object object, String[] properties) {
		JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
		jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
		if (properties == null || properties.length == 0) {
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		} else {
			jsonConfig.setExcludes(properties);
		}
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		return JSONObject.fromObject(object, jsonConfig);
	}

	/**
	 * 将数组转换成JSON 设置JSON-LIB让其过滤掉引起循环的字段
	 * 如果字段为空则设置JSON-LIB的setCycleDetectionStrategy属性让其自己处理循环
	 * 
	 * @author cbc
	 * @date 2017年1月17日
	 * @param object
	 *            对象
	 * @param properties
	 *            对象忽略属性
	 * @return
	 */
	public static JSONArray toJSONArray(Object object, String[] properties) {
		JsonConfig jsonConfig = new JsonConfig(); // 建立配置文件
		jsonConfig.setIgnoreDefaultExcludes(false); // 设置默认忽略
		if (properties == null || properties.length == 0) {
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		} else {
			jsonConfig.setExcludes(properties);
		}
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(object, jsonConfig);
		return jsonArray;
	}
	
	/**
     * 
     * @Description:对象转json，只转换指定的字段，支持级联对象的属性指定
     * 支持集合和单一对象
     * @author:zhujiajian
     * @date 2018年2月12日 下午4:37:27 
     * @param args 多个对象指定输出的属性（可选）
     * @return json 字符串
     */
    public static String objToJson(Object obj,Object[]... args){
    	if(args == null) {
    		return "";
    	}
    	String jsonStr = null;
    	JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    	if(args.length > 0){
    		ComplexPropertyPreFilter cfilter = new ComplexPropertyPreFilter(args);
        	//SerializerFeature.UseSingleQuotes - json字符串中使用单引号
            jsonStr = JSON.toJSONString(obj, cfilter, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
    	}else{
    		jsonStr = JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect,SerializerFeature.WriteDateUseDateFormat);
    	}
    	
    	return jsonStr == null ? null : jsonStr.replaceAll("\\\\", "/");
    }
    /**
	 * 拼接头部
	 * 
	 * @author gaoll
	 * @date 2018-11-24
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String JsonHeader(String message, String status, int size) {
		String jsonTemp =  "{\"head\":{" + "\"message\":\""+message +"\",\"status\":\""+status +"\"},\"data\":{"+
                "\"total\":\""+size + "\",\"dataList\":[";
		return jsonTemp;
	}

	 /**
		 * 拼接头部
		 * 
		 * @author gaoll
		 * @date 2018-11-24
		 * 
		 * @return
		 * @throws Exception
		 */
		public static String JsonHeaderYanZheng(String message, String status, int size) {
			String jsonTemp =  "{\"head\":{" + "\"message\":\""+message +"\",\"status\":\""+status +"\"},\"data\":{";
				jsonTemp = jsonTemp + 
						   "\"verifyResult\":"+"\"" +false+"\"}}"; 
				return jsonTemp;
		}

	
	
}
/**
 * 日期转换器
 * @author wangxf
 * @date 2017-5-17
 */
class JsonDateValueProcessor implements JsonValueProcessor{

	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		return process(arg0);
	}

	@Override
	public Object processObjectValue(String arg0, Object arg1, JsonConfig arg2) {
		return process(arg1);
	}
	private Object process(Object value){  
        if(value instanceof Date){    
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? "" : value.toString();    
    }

	
	

}

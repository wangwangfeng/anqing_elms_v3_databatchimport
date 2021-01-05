package com.zfsoft.batchimport.utils;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author chenjian
 */
public class HttpClient {
    private static Logger logger = LoggerFactory.getLogger(HttpClient.class);

    private static final org.apache.http.client.HttpClient httpClient;

    static {
        httpClient = HttpClientBuilder.create().build();
    }

    /**
     * GET请求方式
     *
     * @param url 调用api地址
     * @return 参数 连接字符串格式
     */
    public static String get(String url) throws Exception {
        String strResult;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse httpResponse = httpClient.execute(httpGet);
        strResult = EntityUtils.toString(httpResponse.getEntity());
        return strResult;
    }

    /**
     * POST请求方式
     *
     * @param uri        调用api地址
     * @param jsonParams 参数 连接字符串格式
     * @return
     * @throws Exception
     */
    public static String post(String uri, String jsonParams) throws Exception {
        String strResult;
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("content-type", "application/json;charset=UTF-8");
        if (!jsonParams.isEmpty()) {
            StringEntity entity = new StringEntity(jsonParams, "UTF-8");
            httpPost.setEntity(entity);
        }
        HttpResponse httpResponse = httpClient.execute(httpPost);
        strResult = EntityUtils.toString(httpResponse.getEntity());
        return strResult;
    }

    /**
     * POST请求方式
     *
     * @param uri   调用api地址
     * @param param 参数 表单形式
     * @return
     */
    public static String post(String uri, Map<String, String> param) {
        if (param == null) {
            return null;
        }
        String strResult;
        HttpPost httpPost = new HttpPost(uri);
        httpPost.addHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        // 创建参数
        List<NameValuePair> map = new ArrayList<>();
        for (String key : param.keySet()) {
            map.add(new BasicNameValuePair(key, param.get(key)));
        }
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(map, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse execute = httpClient.execute(httpPost);
            strResult = EntityUtils.toString(execute.getEntity());
            return strResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String delete(String uri) {
        String strResult;
        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.addHeader("content-type", "application/x-www-form-urlencoded;charset=UTF-8");
        try {
            HttpResponse execute = httpClient.execute(httpDelete);
            strResult = EntityUtils.toString(execute.getEntity());
            return strResult;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json请求
     * @param url
     * @param paramMap
     * @return
     */
    public  static String httpClientPostJson(String url, Map<String, String> paramMap){
        logger.info("[api接口]状态:正在请求:{}",url);
        //POST的URL
        HttpPost httppost=new HttpPost(url);
        //建立HttpPost对象
        List<NameValuePair> params=new ArrayList<NameValuePair>();
        //建立一个NameValuePair数组，用于存储欲传送的参数
        //添加参数
        for(Map.Entry<String, String> s:paramMap.entrySet()){
            params.add(new BasicNameValuePair(s.getKey(), s.getValue()));
        }

        //json请求头部
        httppost.addHeader(HTTP.CONTENT_TYPE,"application/json");

        logger.info("[api接口]状态:发送接口参数:{}", JSON.toJSONString(paramMap));
        //设置编码
        httppost.setEntity(new StringEntity(JSON.toJSONString(paramMap), "UTF-8"));
        CloseableHttpResponse response =null;
        CloseableHttpClient httpclient=null;




        try {
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httppost);
//		    		HttpResponse response=new DefaultHttpClient().execute(httppost);
            //发送Post,并返回一个HttpResponse对象
            if(response.getStatusLine().getStatusCode()==200){//如果状态码为200,就是正常返回
                String result= EntityUtils.toString(response.getEntity());
                logger.info("[api接口]状态:接收到数据:{}",result);
                return result;
            }else{
                logger.info("[api接口]状态:接口请求异常，请求状态码:{}",response.getStatusLine().getStatusCode());
            }
        }catch (Exception e) {
            logger.error("[api接口]状态:请求异常:{}",e.getMessage());
        }finally{
            if(response!=null){
                try {
                    response.close();
                } catch (IOException e) {}
            }

            if(httpclient!=null){
                try {
                    httpclient.close();
                } catch (IOException e) {}
            }
        }
        return null;
    }

}

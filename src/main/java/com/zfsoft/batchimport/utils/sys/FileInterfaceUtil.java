package com.zfsoft.batchimport.utils.sys;

import com.alibaba.fastjson.JSONObject;
import com.zfsoft.batchimport.utils.SM2Util;
import com.zfsoft.batchimport.base.SysCode;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.CharArrayBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description  文件系统上传下载 工具类
 * @author wangsh
 * @date
 * @param
 * @return
 */
public class FileInterfaceUtil {

    private static Logger log = LoggerFactory.getLogger(FileInterfaceUtil.class);

    private static String objectToString(Object obj){
        return obj == null ? "" : obj.toString();
    }

    /**
     * @param param 文件参数
     * @param fis   上传的流 不可为空
     * @return
     * @description 文件上传方法
     * @author wangsh
     * @date
     */
    public static String upLoadFile(Map<String, String> param, InputStream fis) throws Exception {

        String fileNewName = objectToString(param.get("fileName"));
        String fileOriginalName = objectToString(param.get("fileOriginalName"));
        String requestData = FileInterfaceUtil.createFileVoJSon(param);
        // 操作文件流
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("UTF-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        // 文件传输http请求头(multipart/form-data)
        builder.addBinaryBody(fileNewName, fis, ContentType.MULTIPART_FORM_DATA, fileOriginalName);// 文件流

        HttpEntity responseEntity = createFileInterfacePost(FileInterfaceParam.UP_DOWN_URL + "/upLoad.rest", requestData, builder.build());
        if (responseEntity != null) {
            String result = FileInterfaceUtil.responseToString(responseEntity);
            log.info(" 文件上传 : " + result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(!jsonObject.getBoolean("success")) {
                throw new IllegalArgumentException(MessageFormat.format("文件上传到统一文件服务失败，原始文件名称为：{0}", fileOriginalName));
            }
            return result;
        }
        return "{\"success\":\"false\",\"msg\":\"访问失败\"}";
    }


    /**
     * @param requestData 文件主键 或 相对路径
     * @return
     * @description
     * @author wangsh
     * @date 文件下载
     */
    public static InputStream downLoadFile(String requestData) throws Exception {
        requestData = requestData.replaceAll("//", "/");
        HttpEntity responseEntity = createFileInterfacePost(FileInterfaceParam.UP_DOWN_URL + "/download.rest", requestData, null);
        if (responseEntity != null) {
            return responseEntity.getContent();
        }
        return null;
    }

    /**
     * @param requestData 文件主键 或 相对路径
     * @return
     * @description
     * @author wangsh
     * @date 文件下载
     */
    public static InputStream getFileInputStream(String requestData) throws Exception {
        requestData = requestData.replaceAll("//", "/");
        HttpEntity responseEntity = createFileInterfacePost(FileInterfaceParam.UP_DOWN_URL + "/download.rest", requestData, null);
        if (responseEntity != null) {
            return responseEntity.getContent();
        }
        return null;
    }

    /**
     * 文件删除 -- 物理删除
     * @param requestData 文件主键 或 相对路径
     * @return
     */
    public static boolean delFile(String requestData) {
        requestData = requestData.replaceAll("//", "/");
        HttpEntity responseEntity = createFileInterfacePost(FileInterfaceParam.OTHER_URL + "/delFile.rest", requestData, null);
        if (responseEntity != null) {
            String msgStr = FileInterfaceUtil.responseToString(responseEntity);
            JSONObject jsonObject = JSONObject.parseObject(msgStr);
            if(jsonObject.getBoolean("success") != null && jsonObject.getBoolean("success")) {
                return true;
            } else {
                throw new IllegalArgumentException(MessageFormat.format("临时文件删除失败，文件ID/保存路径：{0}", requestData));
            }
        }
        return false;
    }

    /**
     * 判断文件是否存在
     * @param requestData 文件主键 或 相对路径
     * @return
     * @throws Exception
     */
    public static Boolean existFile(String requestData) throws Exception {
        InputStream inputStream = FileInterfaceUtil.downLoadFile(requestData);
        if (inputStream != null) {
            return true;
        }
        return false;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @return 远程资源的响应结果
     * @author wangsh
     * @date 2018年10月19日
     */
    public static String sendPost(String url, String requestData) throws Exception {
        HttpEntity responseEntity = createFileInterfacePost(FileInterfaceParam.OTHER_URL + url, requestData, null);
        if (responseEntity != null) {
            String result = FileInterfaceUtil.responseToString(responseEntity);
            log.info(result);
            return result;
        }
        return "{\"success\":\"false\",\"msg\":\"访问失败\"}";
    }

    private static String responseToString(HttpEntity entity) {
        try {
            InputStream instream = entity.getContent();
            Reader reader = new InputStreamReader(instream, Charset.forName("UTF-8"));
            CharArrayBuffer buffer = new CharArrayBuffer(4096);
            char[] tmp = new char[1024];

            int l;
            while ((l = reader.read(tmp)) != -1) {
                buffer.append(tmp, 0, l);
            }
            return buffer.toString();
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * @param
     * @return
     * @description 拼接参数
     * @author wangsh
     * @date
     */
    private static HttpEntity createFileInterfacePost(String url, String requestData, HttpEntity entity) {
        try {
            // 拼接参数 , 密文
            List<NameValuePair> pairs = new ArrayList<>();
            Map<String, String> params = genrateParam(requestData);
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            URI uri = new URIBuilder(url).setParameters(pairs).setCharset(Charset.forName("UTF-8")).build();

            HttpPost httpPost = new HttpPost(uri);
            // 附件等信息
            if (entity != null) httpPost.setEntity(entity);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                return response.getEntity();
            }else{
                System.out.println( response.getStatusLine().getStatusCode()  );
            }
        } catch (Exception e) {
            throw new RuntimeException("网络连接异常" , e);
        }
        return null;
    }

    /**
     * @param
     * @return 参数map
     * @description 加密并 拼接参数
     * @author wangsh
     * @date
     */
    public static Map<String, String> genrateParam(String requestData) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        // 参数传递顺序不可打乱，防止验签错误
        String RequestData = SM2Util.sm2Encrypt(FileInterfaceParam.PUBLIC_KEY ,requestData);
        params.put("RequestData", RequestData );
        // 时间戳 , 五分钟过期
        String timeStamp = String.valueOf(System.currentTimeMillis());
        String dataSign = SM2Util.sm2Sign(FileInterfaceParam.PRIVATE_KEY , FileInterfaceParam.ApiKey , timeStamp );
        params.put("DataSign", URLEncoder.encode(dataSign, "UTF-8"));
        params.put("TimeStamp" , timeStamp );
        params.put("ApiKey", FileInterfaceParam.ApiKey);
        return params;
    }


    public static Map<String, String> createFileInfoMap(String fileOid, String fileName, String fileOriginalName, String filePath){
        return FileInterfaceUtil.createFileInfoMap( fileOid , fileName, fileOriginalName, filePath
                ,  "1",  SysCode.DELETE_STATUS.YES,  "3"
                ,  null,  null,  null,  null
                ,  null,  null,  null
                ,  null,  null,  null,  null,  null);
    }

    /**
     * @param fileOid            atta 附件主键, 若为空 , 需要绑定接口返回主键
     * @param fileName           文件保存名称
     * @param fileOriginalName   文件原名
     * @param filePath           文件保存相对路径
     * @param fileStatus         文件状态 0 临时文件 1 正式上传文件(默认) , 临时文件会定期清理
     * @param needCompress       压缩标记 N 无需压缩(默认) , Y 需要压缩 默认0.5f  , 也可传 0-1的float , 只支持图片
     * @param filelevel          文件等级 , 3级以下文件会被自动归档
     * @param userName           用户名(文件所属用户用户名称)
     * @param userType           用户类型 COMMON 个人 ,  LEGAL-法人
     * @param idCard             身份证号码
     * @param agencyCode         统一信用代码
     * @param groupCode          文件分类编码
     * @param groupName          文件分类名称
     * @param groupDescription   文件分类描述  --  文件分类编码相同视为同一个分类 , 保存时会更新
     * @param serviceCode        事项编码
     * @param serviceName        事项名称
     * @param serviceDescription 事项描述
     * @param serviceOrganOid    事项所属机构主键
     * @param regId              办件id
     * @return
     * @description 生成文件参数 map
     * @author wangsh
     * @date
     */
    public static Map<String, String> createFileInfoMap(String fileOid, String fileName, String fileOriginalName, String filePath
            , String fileStatus, String needCompress, String filelevel
            , String userName, String userType, String idCard, String agencyCode
            , String groupCode, String groupName, String groupDescription
            , String serviceCode, String serviceName, String serviceDescription, String serviceOrganOid, String regId
    ) {
        Map<String, String> fileParam = new HashMap<>();
        fileParam.put("userName", userName);
        fileParam.put("userType", StringUtils.isNotEmpty(userType) ? userType : "SYSTEM");
        fileParam.put("idCard", idCard);
        fileParam.put("agencyCode", agencyCode);

        fileParam.put("fileOid", fileOid);
        fileParam.put("fileName", fileName);
        fileParam.put("fileOriginalName", fileOriginalName);
        fileParam.put("downDir", filePath);
        fileParam.put("fileStatus", fileStatus);
        fileParam.put("filelevel", filelevel);
        fileParam.put("needCompress", needCompress);

        fileParam.put("groupCode", groupCode);
        fileParam.put("groupName", groupName);
        fileParam.put("groupDescription", groupDescription);

        fileParam.put("serviceCode", serviceCode);
        fileParam.put("serviceName", serviceName);
        fileParam.put("serviceDescription", serviceDescription);
        fileParam.put("serviceOrganOid", serviceOrganOid);
        fileParam.put("regId", regId);

        return fileParam;
    }

    /**
     * @param
     * @return
     * @description 生成文件参数json
     * @author wangsh
     * @date
     */
    public static String createFileVoJSon(Map<String, String> fileParam) {
        String json = "{" +
                "\"oid\": \"" + fileParam.get("fileOid") + "\"," +
                "\"userName\": \"" + fileParam.get("userName") + "\"," +
                "\"userType\": \"" + fileParam.get("userType") + "\"," +
                "\"idCard\": \"" + fileParam.get("idCard") + "\"," +
                "\"agencyCode\": \"" + fileParam.get("agencyCode") + "\"," +
                "\"downDir\": \"" + fileParam.get("downDir") + "\"," +
                "\"fileStatus\": \"" + fileParam.get("fileStatus") + "\"," +
                "\"fileGroup\": {" +
                "\"code\": \"" + fileParam.get("groupCode") + "\"," +
                "\"name\": \"" + fileParam.get("groupName") + "\"," +
                "\"description\": \"" + fileParam.get("groupDescription") + "\"" +
                "}," +
                "\"filelevel\": \"" + fileParam.get("filelevel") + "\"," +
                "\"service\": {" +
                "\"serviceCode\": \"" + fileParam.get("serviceCode") + "\"," +
                "\"serviceName\": \"" + fileParam.get("serviceName") + "\"," +
                "\"districtOid\": \"" + fileParam.get("serviceDescription") + "\"," +
                "\"organOid\": \"" + fileParam.get("serviceOrganOid") + "\"" +
                "}," +
                "\"regId\": \"" + fileParam.get("regId") + "\"," +
                "\"needCompress\": \"" + fileParam.get("needCompress") + "\"" +
                "}";
        return json.replaceAll("null","");
    }

}

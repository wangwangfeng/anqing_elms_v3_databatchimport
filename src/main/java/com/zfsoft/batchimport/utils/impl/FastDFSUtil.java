package com.zfsoft.batchimport.utils.impl;

import com.zfsoft.batchimport.utils.PropertiesUtil;
import org.apache.commons.io.IOUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * FastDFSUtil 文件处理工具类downloadFileForFastDFS
 *
 * @author yuy
 * @date 20201029
 */
@Repository("fastDFSUtil")
public class FastDFSUtil {
    static PropertiesUtil env = new PropertiesUtil("prop/common.properties");
    private static String CONFIG_FILENAME = "prop/"+env.readProperty("profiles.env")+"/fdfs_client.conf";
    private static StorageClient1 storageClient1 = null;

    private static TrackerClient trackerClient = null;

    private static StorageServer storageServer = null;


    // 初始化FastDFS Client
    static {
        try {
            ClassPathResource cpr = new ClassPathResource(CONFIG_FILENAME);
            ClientGlobal.init(FastDFSUtil.class.getResource("/").getPath()+CONFIG_FILENAME);
            trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
            TrackerServer trackerServer = trackerClient.getConnection();
            if (trackerServer == null) {
                throw new IllegalStateException("getConnection return null");
            }
            storageServer = trackerClient.getStoreStorage(trackerServer);
            if (storageServer == null) {
                throw new IllegalStateException("getStoreStorage return null");
            }
            storageClient1 = new StorageClient1(trackerServer,storageServer);
            ProtoCommon.activeTest(storageServer.getSocket());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @author yuy
     * @date 20201029
     * @param is 文件流对象
     * @param fileName 文件名
     * @return
     */
    public static String uploadFile(InputStream is, String fileName) {
        try {
            InputStream nis = null;
            if (is != null) {
                    nis = is;
            }
            byte[] buff = IOUtils.toByteArray(nis);
            is.close();
            NameValuePair[] nameValuePairs = null;
            TrackerServer trackerServer = trackerClient.getConnection();
            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
            StorageClient1 storageClient=  new StorageClient1(trackerServer,storageServer);
            return storageClient.upload_file1(buff, FastDFSUtil.getExtensionName(fileName),nameValuePairs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @Description: InputStream 写入文件
     * @Param: [destination, input]
     * @author yuy
     * @date 20201029
     * @Return: void
     **/
    private static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        //input.close();
    }

    /**
     * 获取文件元数据
     * @param fileId 文件ID
     * @return
     */
    public static Map<String,String> getFileMetadata(String fileId) {
        try {
            NameValuePair[] metaList = storageClient1.get_metadata1(fileId);
            if (metaList != null) {
                HashMap<String,String> map = new HashMap<String, String>();
                for (NameValuePair metaItem : metaList) {
                    map.put(metaItem.getName(),metaItem.getValue());
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     * @param fileId 文件ID
     * @return 删除失败返回-1，否则返回0
     */
    public static int deleteFile(String fileId) {
        try {
            return storageClient1.delete_file1(fileId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 下载文件
     * @param fileId 文件ID（上传文件成功后返回的ID）
     * @return
     */
    public static synchronized InputStream downloadFileForFastDFS(String fileId) {
        try {
//            trackerClient = new TrackerClient();
//            TrackerServer trackerServer = trackerClient.getConnection();
//            StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
//            ProtoCommon.activeTest(storageServer.getSocket());
//            StorageClient1 storageClient=  new StorageClient1(trackerServer,storageServer);
            byte[] content = storageClient1.download_file1(fileId);
            InputStream inputStream = new ByteArrayInputStream(content);
            return inputStream;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
     * Java文件操作 获取文件扩展名
     *
     * @author yuy
     * @date 20201029
     */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
    /*
     * Java文件操作 获取不带扩展名的文件名
     *
     * @author yuy
     * @date 20201029
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }


}

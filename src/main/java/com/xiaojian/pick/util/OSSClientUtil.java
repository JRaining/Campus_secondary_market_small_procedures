package com.xiaojian.pick.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.Random;

public class OSSClientUtil {
    Log log = LogFactory.getLog(OSSClientUtil.class);

    //阿里云OSS地址，这里看根据你的oss选择（选用自己的）
    protected static String endpoint = "xxx";
    //阿里云OSS账号（选用自己的）
    protected static String accessKeyId  = "xxx";
    //阿里云OSS密钥（选用自己的）
    protected static String accessKeySecret  = "xxx";
    //阿里云OSS上的存储块bucket名字（选用自己的）
    protected static String bucketName  = "xxx";

    //默认阿里云图片文件存储目录
    private String homeimagedir = "commodity/" + StringUtil.getDateForm() + "/";

    public String getHomeimagedir() {
        return homeimagedir;
    }
    public void setHomeimagedir(String homeimagedir) {
        this.homeimagedir = homeimagedir;
    }

    private OSSClient ossClient;
    public OSSClientUtil() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    /**
     * 初始化
     */
    public void init() {
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }
    /**
     * 销毁
     */
    public void destory() {
        if(ossClient != null){
            ossClient.shutdown();
        }
    }

    /**
     * 图片上传阿里云oss，表单上传，更改文件名
     * @param file
     * @return  文件名
     */
    public String uploadHomeImageOSS(MultipartFile file,Boolean randomName) throws Exception {
        if (file.getSize() > 1024 * 1024 * 5) {
            throw new Exception("上传图片大小不能超过5M！");
        }
        String originalFilename = file.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String fileName = "";
        if(randomName){
            Random random = new Random();
            fileName = random.nextInt(10000) + System.currentTimeMillis() + substring;
        } else{
            fileName = originalFilename;
        }
        try {
            InputStream inputStream = file.getInputStream();
            this.uploadHomeImageFileOSS(inputStream, fileName);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("图片上传失败");
        }
    }


    /**
     * 图片上传到OSS服务器 ，文件流上传
     * 如果同名文件会覆盖服务器上的
     * @param instream 文件流
     * @param fileName 文件名称 包括后缀名
     * @return  出错返回"" ,唯一MD5数字签名
     */
    public String uploadHomeImageFileOSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("attachment=filename;");
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName, homeimagedir + fileName, instream,objectMetadata);
            // 唯一MD5数字签名
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * 判断OSS服务文件上传时文件的类型contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

    /**
     * 获得url链接
     *
     * @param fileName
     * @return
     */
    public String getUrl(String fileName) {
        if(fileName != null){
            // 图片完整路径
            // 文件url地址路径，精确到文件名上一层目录
            String img_src = "https://" + bucketName + "." + endpoint + "/" + homeimagedir + fileName;
            return img_src;
        }
        return null;
    }

    /**
     * 上传图片，并返回图片完整路径
     * @param file
     * @return
     * @throws Exception
     */
    public String uploadImageAndGetPath(MultipartFile file,Boolean randomName) throws Exception {
        OSSClientUtil ossClient = new OSSClientUtil();
        if (file == null || file.getSize() <= 0) {
            throw new Exception("图片不能为空");
        }
        String name = this.uploadHomeImageOSS(file,randomName);
        String imgUrl = this.getUrl(name);
        return imgUrl;
    }

}

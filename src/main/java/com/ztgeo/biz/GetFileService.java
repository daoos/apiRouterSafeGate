package com.ztgeo.biz;

import com.ztgeo.utils.FtpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Objects;

@Service
public class GetFileService {

    private Logger log = LoggerFactory.getLogger(GetFileService.class);

    @Value("${customAttributes.ftpIp}")
    private String ftpIp;
    @Value("${customAttributes.ftpPort}")
    private int ftpPort;
    @Value("${customAttributes.ftpUsername}")
    private String ftpUsername;
    @Value("${customAttributes.ftpPassword}")
    private String ftpPassword;

    /**
     * 把request请求中的文件上传到FTP服务器
     *
     * @param request HttpServletRequest对象
     * @param ftpUid  用户名
     */
    public void getuploadFile(HttpServletRequest request, String ftpUid) {
        try {
            //获取文件存储日期
            DateTimeFormatter df = DateTimeFormatter.ofPattern("/yyyy/MM/dd");
            LocalDate today = LocalDate.now();
            String dateStr = df.format(today);
            String Uid = dateStr + "/" + ftpUid;
            //判断request是否有文件
            if (isMultipart(request)) { // multipartResolver.isMultipart(request)
                // 将request变成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                Iterator<String> iter = multiRequest.getFileNames();
                // 获取multiRequest 中所有的文件名
                while (iter.hasNext()) {
                    MultipartFile multipartFile = multiRequest.getFile(iter.next());
                    String fileName = multipartFile.getOriginalFilename();
                    FtpUtil.uploadFile(ftpIp, ftpPort, ftpUsername, ftpPassword, Uid, fileName, multiRequest.getInputStream());
                }
            }
        } catch (Exception ex) {
            log.error("解析MultipartRequest错误", ex);
        }
    }

    /**
     * 判断request是否为MultipartRequest
     */
    private boolean isMultipart(HttpServletRequest request) {
        if (!Objects.equals(null, request.getContentType()) && request.getContentType().startsWith("multipart/")) {
            return true;
        }
        return false;
    }

}

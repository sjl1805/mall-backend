package com.example.util;

import com.example.common.ResultCode;
import com.example.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.annotation.PostConstruct;

/**
 * 文件工具类
 */
@Slf4j
@Component
public class FileUtil {

    /**
     * 文件访问URL前缀
     */
    private static final String FILE_URL_PREFIX = "/uploads/";
    /**
     * 允许的文件类型
     */
    private static final String[] ALLOWED_FILE_TYPES = {
            "image/jpeg", "image/jpg", "image/png", "image/gif",
            "application/pdf", "application/vnd.ms-excel",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
    };
    /**
     * 最大文件大小（10MB）
     */
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;
    /**
     * 文件上传路径
     */
    @Value("${file.upload.path}")
    private String uploadPath;

    /**
     * 初始化时检查文件上传路径
     */
    @PostConstruct
    public void init() {
        log.info("文件上传路径配置: {}", uploadPath);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            try {
                boolean mkdirResult = uploadDir.mkdirs();
                if (!mkdirResult) {
                    log.error("创建文件上传根目录失败，可能因为权限问题: {}", uploadPath);
                } else {
                    log.info("成功创建文件上传根目录: {}", uploadPath);
                }
            } catch (Exception e) {
                log.error("创建文件上传目录时发生异常: {}", e.getMessage(), e);
            }
        } else {
            log.info("文件上传目录已存在: {}", uploadPath);
        }
        
        // 检查目录是否可写
        if (!uploadDir.canWrite()) {
            log.error("文件上传目录不可写，请检查权限设置: {}", uploadPath);
        } else {
            log.info("文件上传目录可写: {}", uploadPath);
        }
        
        // 相对路径转绝对路径以便于日志显示
        try {
            log.info("文件上传目录绝对路径: {}", uploadDir.getCanonicalPath());
        } catch (IOException e) {
            log.warn("获取文件上传目录绝对路径失败: {}", e.getMessage());
        }
    }

    /**
     * 上传文件
     *
     * @param file 文件
     * @return 文件访问路径
     */
    public String uploadFile(MultipartFile file) {
        // 文件校验
        validateFile(file);

        // 生成日期路径，例如：2023/04/25
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        log.info("生成日期路径: {}", datePath);

        // 生成文件保存目录
        String dirPath = uploadPath + File.separator + datePath;
        log.info("文件保存目录: {}", dirPath);
        
        File dir = new File(dirPath);
        if (!dir.exists()) {
            try {
                boolean mkdirsResult = dir.mkdirs();
                if (!mkdirsResult) {
                    log.error("创建文件目录失败: {}", dirPath);
                    throw new BusinessException("创建文件目录失败", ResultCode.SYSTEM_ERROR);
                }
                log.info("创建目录成功: {}", dirPath);
            } catch (Exception e) {
                log.error("创建目录时发生异常: {}", e.getMessage(), e);
                throw new BusinessException("创建文件目录失败: " + e.getMessage(), ResultCode.SYSTEM_ERROR);
            }
        }

        // 生成唯一文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = StringUtils.getFilenameExtension(originalFilename);
        String newFilename = UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
        log.info("生成的文件名: {}", newFilename);

        // 文件保存路径
        String filePath = dirPath + File.separator + newFilename;
        log.info("文件完整保存路径: {}", filePath);

        try {
            log.info("开始保存文件...");
            // 保存文件
            File destFile = new File(filePath);
            file.transferTo(destFile);
            
            // 验证文件是否成功写入
            if (!destFile.exists() || destFile.length() == 0) {
                log.error("文件写入失败或文件大小为0: {}", filePath);
                throw new BusinessException("文件保存失败", ResultCode.SYSTEM_ERROR);
            }
            
            log.info("文件保存成功，文件大小: {} bytes", destFile.length());

            // 返回文件访问路径
            String fileUrl = FILE_URL_PREFIX + datePath + "/" + newFilename;
            log.info("生成文件访问URL: {}", fileUrl);
            return fileUrl;
        } catch (IOException e) {
            log.error("文件上传失败: {} - {}", e.getClass().getName(), e.getMessage(), e);
            throw new BusinessException("文件上传失败: " + e.getMessage(), ResultCode.SYSTEM_ERROR);
        } catch (Exception e) {
            log.error("文件上传过程发生未预期异常: {}", e.getMessage(), e);
            throw new BusinessException("文件上传失败: " + e.getMessage(), ResultCode.SYSTEM_ERROR);
        }
    }

    /**
     * 获取文件完整路径
     *
     * @param fileUrl 文件访问路径
     * @return 文件完整路径
     */
    public String getFullPath(String fileUrl) {
        log.info("获取文件完整路径, fileUrl: {}", fileUrl);
        
        if (!StringUtils.hasText(fileUrl)) {
            log.warn("文件URL为空");
            return null;
        }
        
        // 先标准化fileUrl中的路径分隔符
        fileUrl = fileUrl.replace('\\', '/');
        
        if (!fileUrl.startsWith(FILE_URL_PREFIX)) {
            log.warn("文件URL不以{}开头: {}", FILE_URL_PREFIX, fileUrl);
            
            // 尝试处理已经是相对路径的情况
            if (fileUrl.startsWith("/")) {
                fileUrl = fileUrl.substring(1); // 去掉开头的斜杠
            }
            
            // 构建相对路径
            String relativePath = fileUrl;
            File file = new File(uploadPath, relativePath);
            String fullPath = file.getPath();
            log.info("尝试直接拼接路径: {}", fullPath);
            
            // 检查文件是否存在
            if (file.exists() && file.isFile()) {
                return fullPath;
            }
            
            return null;
        }

        // 获取相对路径部分
        String relativePath = fileUrl.substring(FILE_URL_PREFIX.length());
        
        // 尝试不同的路径组合来查找文件
        File file = null;
        String fullPath = null;
        
        // 方法1：使用标准方式
        file = new File(uploadPath, relativePath);
        fullPath = file.getPath();
        log.info("尝试方法1 - 标准相对路径: {}", fullPath);
        if (file.exists() && file.isFile()) {
            log.info("文件存在(方法1): {}", fullPath);
            return fullPath;
        }
        
        // 方法2：尝试直接在uploads目录下查找文件名
        String fileName = new File(relativePath).getName(); // 获取文件名部分
        file = new File(uploadPath, fileName);
        fullPath = file.getPath();
        log.info("尝试方法2 - 直接使用文件名: {}", fullPath);
        if (file.exists() && file.isFile()) {
            log.info("文件存在(方法2): {}", fullPath);
            return fullPath;
        }
        
        // 方法3：尝试使用绝对路径
        file = new File(uploadPath).getAbsoluteFile();
        File fileWithRelative = new File(file, relativePath);
        fullPath = fileWithRelative.getPath();
        log.info("尝试方法3 - 使用绝对路径: {}", fullPath);
        if (fileWithRelative.exists() && fileWithRelative.isFile()) {
            log.info("文件存在(方法3): {}", fullPath);
            return fullPath;
        }
        
        // 方法4：尝试使用绝对路径+文件名
        file = new File(uploadPath).getAbsoluteFile();
        File fileWithName = new File(file, fileName);
        fullPath = fileWithName.getPath();
        log.info("尝试方法4 - 绝对路径+文件名: {}", fullPath);
        if (fileWithName.exists() && fileWithName.isFile()) {
            log.info("文件存在(方法4): {}", fullPath);
            return fullPath;
        }
        
        // 所有方法都失败，返回最可能的路径
        log.warn("文件不存在，返回标准路径: {}", new File(uploadPath, relativePath).getPath());
        return new File(uploadPath, relativePath).getPath();
    }
     /**
     * 删除文件
     *
     * @param fileUrl 文件访问路径
     * @return 是否删除成功
     */
    public boolean deleteFile(String fileUrl) {
        if (!StringUtils.hasText(fileUrl)) {
            return false;
        }
        
        // 先标准化fileUrl中的路径分隔符
        fileUrl = fileUrl.replace('\\', '/');
        
        if (!fileUrl.startsWith(FILE_URL_PREFIX)) {
            return false;
        }

        // 获取相对路径部分
        String relativePath = fileUrl.substring(FILE_URL_PREFIX.length());
        // 使用File构造器来正确处理路径分隔符
        File file = new File(uploadPath, relativePath);

        // 删除文件
        if (file.exists() && file.isFile()) {
            return file.delete();
        }

        return false;
    }
    /**
     * 文件校验
     *
     * @param file 文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("文件不能为空", ResultCode.PARAM_ERROR);
        }

        // 检查文件大小
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new BusinessException("文件大小不能超过10MB", ResultCode.PARAM_ERROR);
        }

        // 检查文件类型
        String contentType = file.getContentType();
        boolean isAllowedType = false;
        for (String allowedType : ALLOWED_FILE_TYPES) {
            if (allowedType.equals(contentType)) {
                isAllowedType = true;
                break;
            }
        }

        if (!isAllowedType) {
            throw new BusinessException("不支持的文件类型", ResultCode.PARAM_ERROR);
        }
    }
} 
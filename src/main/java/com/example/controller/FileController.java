package com.example.controller;

import com.example.common.Result;
import com.example.util.FileUtil;
import com.example.util.UserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件控制器
 */
@Slf4j
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUtil fileUtil;
    private final UserUtil userUtil;

    /**
     * 通用文件上传接口
     *
     * @param file 文件
     * @return 文件访问路径
     */
    @PostMapping("/upload")
    public Result<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return Result.error("请选择文件", 400);
        }
        // 上传文件
        String fileUrl = fileUtil.uploadFile(file);
        return Result.success(fileUrl, "文件上传成功");
    }

    /**
     * 管理员文件上传接口
     *
     * @param file 文件
     * @return 文件访问路径
     */
    @PostMapping("/admin/upload")
    public Result<String> adminUploadFile(@RequestParam("file") MultipartFile file) {
        // 验证是否为管理员
        if (!userUtil.isAdmin()) {
            return Result.error("非管理员禁止操作", 403);
        }

        if (file.isEmpty()) {
            return Result.error("请选择文件", 400);
        }

        // 上传文件
        String fileUrl = fileUtil.uploadFile(file);
        return Result.success(fileUrl, "文件上传成功");
    }

    /**
     * 通用文件查看接口
     *
     * @param fileUrl 文件URL
     * @return 文件资源
     */
    @GetMapping("/view")
    public ResponseEntity<Resource> viewFile(@RequestParam("fileUrl") String fileUrl) {
        log.info("请求访问文件: {}", fileUrl);
        
        if (!StringUtils.hasText(fileUrl)) {
            log.warn("文件URL为空");
            return ResponseEntity.badRequest().build();
        }
        
        // 获取文件完整路径
        String fullPath = fileUtil.getFullPath(fileUrl);
        log.info("文件完整路径: {}", fullPath);
        
        if (fullPath == null) {
            log.warn("无法获取文件完整路径，fileUrl: {}", fileUrl);
            return ResponseEntity.notFound().build();
        }

        // 创建文件资源
        File file = new File(fullPath);
        if (!file.exists() || !file.isFile()) {
            log.warn("文件不存在或不是文件: {}", fullPath);
            return ResponseEntity.notFound().build();
        }
        
        log.info("文件存在，大小: {} bytes", file.length());

        try {
            // 获取文件MIME类型
            Path path = Paths.get(fullPath);
            String contentType = Files.probeContentType(path);
            if (contentType == null) {
                contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
            }
            log.info("文件MIME类型: {}", contentType);

            // 创建文件资源
            Resource resource = new FileSystemResource(file);

            // 设置响应头
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (IOException e) {
            log.error("文件查看失败: {}", e.getMessage(), e);
            return ResponseEntity.internalServerError().build();
        }
    }
} 
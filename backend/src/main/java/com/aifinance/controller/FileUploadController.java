package com.aifinance.controller;

import com.aifinance.utils.GCSUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> upload(@RequestParam("file") MultipartFile file) {
        String objectName = file.getOriginalFilename(); // 获取文件名

        try {
            // 将 MultipartFile 转换为临时文件
            File tempFile = convertToFile(file);

            // 使用 GCSUtil 上传文件
            String publicUrl = GCSUtil.uploadFile(objectName, tempFile.getAbsolutePath());

            // 删除临时文件
            tempFile.delete();

            // 返回文件的 URL
            Map<String, Object> response = new HashMap<>();
            response.put("url", publicUrl);
            response.put("message", "File uploaded successfully");

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Failed to upload file: " + e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 将 MultipartFile 转换为临时文件的方法
    private File convertToFile(MultipartFile file) throws IOException {
        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(file.getBytes());
        }
        return tempFile;
    }
}

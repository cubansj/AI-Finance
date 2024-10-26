package com.aifinance.controller;

<<<<<<< HEAD
import com.aifinance.pojo.Result;
import com.aifinance.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;
=======
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
>>>>>>> ba47ff84eb24d378597c964f1dcc2b27dc81be62

@RestController
public class FileUploadController {

    @PostMapping("/upload")
<<<<<<< HEAD
    public Result<String> upload(MultipartFile file) throws Exception {
        //把文件的内容存储到本地磁盘上
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename="+ originalFilename);
        //保证文件的名字是唯一的,从而防止文件覆盖
        String filename = UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //file.transferTo(new File("C:\\Users\\Administrator\\Desktop\\files\\"+filename));
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(url);
=======
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
>>>>>>> ba47ff84eb24d378597c964f1dcc2b27dc81be62
    }
}

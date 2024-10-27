package com.aifinance.utils;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class GCSUtil {

    private static final String BUCKET_NAME = "bucket-5620-ai-finance"; // 存储桶名称
    @Value("${gcs.credentials.jsonPath}")
        private String jsonPath;

    private static String staticJsonPath;

    @PostConstruct
    public void init() {
        staticJsonPath = jsonPath;
    }

    // 初始化 GCS 存储客户端
    private static Storage getStorage() throws Exception {
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(staticJsonPath)))
                .build()
                .getService();
    }

    // 上传文件并返回公开 URL
    public static String uploadFile(String objectName, String filePath) throws Exception {
        Storage storage = getStorage(); // 获取 Storage 实例

        // 创建 BlobId 和 BlobInfo
        BlobId blobId = BlobId.of(BUCKET_NAME, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

        // 上传文件
        try (InputStream input = Files.newInputStream(Paths.get(filePath))) {
            storage.create(blobInfo, input);
        }

        // 设置文件的访问权限为公开可读
        storage.createAcl(blobId, Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));

        // 返回公开访问 URL
        return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, objectName);
    }
}

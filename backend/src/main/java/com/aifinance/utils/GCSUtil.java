package com.aifinance.utils;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GCSUtil {

    private static final String BUCKET_NAME = "bucket-5620-ai-finance"; // 存储桶名称
    private static final String JSON_PATH = "D:\\Documents\\2024S2\\5620\\5620Project\\backend\\project-439505-7a2ea3d63268.json"; // 替换为你的 JSON 文件路径

    // 初始化 GCS 存储客户端
    private static Storage getStorage() throws Exception {
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(JSON_PATH)))
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

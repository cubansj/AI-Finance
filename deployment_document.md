
# 项目部署文档

## 1. 项目结构说明
```plaintext
5620Project
│
├── backend [ai-finance]            # 后端服务
│   ├── Dockerfile                  # 构建后端镜像
│   ├── pom.xml                     # Maven 配置文件
│   ├── src/                        # 后端代码
│   └── target/                     # 打包后的 Spring Boot 应用
│
├── frontend                        # 前端项目
│   ├── dist/                       # 前端打包文件
│   ├── Dockerfile                  # 构建前端镜像
│   └── vite.config.js              # Vite 配置文件
└── nginx.conf                      # Nginx 配置文件
```

---

## 2. 环境准备

1. **安装 Docker**  
   确保 Docker 已安装并正在运行。

2. **创建 Docker 网络：**
   ```bash
   docker network create my-network
   ```

---

## 3. Redis 服务配置与启动

### 使用环境变量配置 Redis 服务

后端服务的 `application.yml` 文件中使用了 **环境变量** 方式配置 Redis 连接：

```yaml
spring:
  redis:
    host: ${SPRING_REDIS_HOST:localhost}  # 优先使用环境变量 SPRING_REDIS_HOST
    port: 6379  # 默认端口
```

### 配置环境变量步骤

在 **Windows** 中设置环境变量：

```powershell
$env:SPRING_REDIS_HOST="redis"
```

验证环境变量：

```powershell
echo $env:SPRING_REDIS_HOST
```

---

## 4. Docker 容器构建与启动

### 4.1 后端服务部署

1. **构建后端镜像：**
   在 `backend` 目录中运行：
   ```bash
   docker build -t aifinance-backend .
   ```

2. **启动后端容器：**
   ```bash
   docker run -d --name aifinance-backend --network my-network -p 8080:8080 aifinance-backend
   ```

### 4.2 启动 Redis 服务

```bash
docker run -d --name redis --network my-network redis
```

---

## 5. Nginx 服务配置与部署

### 5.1 Nginx 配置文件 (`nginx.conf`)

```nginx

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    sendfile        on;

    keepalive_timeout  65;

    server {
        listen       80;
        server_name  localhost;

        location / {
            root /usr/share/nginx/html;
            index index.html index.htm;
            try_files $uri /index.html;
        }

        #error_page  404              /404.html;

        # redirect server error pages to the static page /50x.html
        #
        error_page   500 502 503 504  /50x.html;
        location = /50x.html {
            root   html;
        }
        
        location /api/ {
            rewrite ^/api/(.*) /$1 break;  
            proxy_pass http://aifinance-backend:8080/;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header X-Forwarded-Proto $scheme;
        }
    }
}

```

### 5.2 部署 Nginx 容器

1. **确保本地的 `nginx.conf` 文件路径正确。**

2. **启动 Nginx 容器：**
   ```bash
   docker run -d --name nginx --network my-network -p 81:80 \    
   -v D:/Documents/2024S2/5620/5620Project/nginx.conf:/etc/nginx/nginx.conf:ro \   
   -v D:/Documents/2024S2/5620/5620Project/frontend/dist:/usr/share/nginx/html:ro nginx
   ```
   其中，D:/Documents/2024S2/5620/5620Project/nginx.conf是nginx.conf的路径
      D:/Documents/2024S2/5620/5620Project/frontend/dist是前端打包后的文件路径。请根据自己的实际情况修改

3. **验证 Nginx 是否正常运行：**
   ```bash
   docker exec -it nginx nginx -t
   docker logs nginx
   ```
   
---

## 6. 项目验证

1. **访问前端页面：**
   打开浏览器，访问：`http://localhost:81`，应该会跳转到登录页面，登录后功能可以进入系统进行正常操作。

   




# 使用官方 Node 镜像构建前端应用
FROM node:16-alpine as build-stage

# 设置工作目录
WORKDIR /app

# 复制项目文件
COPY package*.json ./
RUN npm install

COPY . .

# 构建前端项目
RUN npm run build

# 使用 Nginx 作为生产环境的 Web 服务器
FROM nginx:alpine
COPY --from=build-stage /app/dist /usr/share/nginx/html

# 暴露 80 端口
EXPOSE 80

# 启动 Nginx
CMD ["nginx", "-g", "daemon off;"]

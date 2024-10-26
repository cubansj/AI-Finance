# Part1 Overview

---

# Part2 Configuration

## Backend Setup

### 1.Install MySQL

**Download and Install MySQL**
Visit the MySQL official website to download the MySQL Community Server for your operating system: [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).Version 8.0.39 is what we use now. 
Follow the installation guide provided during the download process. The installation wizard will guide you through the setup.

**Configure MySQL**
During installation, you will be prompted to set the root password. Do remember your password and make sure MySQL is set to run on the default port `3306`.
Next you will need to create a database. Inside the database, you will create the following table. 
Run the SQL query in the `5620_aifinance.sql` file to create necessary table for the project.

**Connect Backend to MySQL using IntelliJ IDEA**
Navigate to database in the IDEA ide, and add mysql as your datasource. 
Connect to your mysql database using your user name and password your set when configure MySQL. 

---

### 2.Configure `application.yml`:
Navigate to `src/main/resources/application.yml` in your project. Update the following lines to ensure the application connects to your MySQL instance:

```yml
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```
Replace `your_database_name` with the name of your database. So do the username and password.

---

### 3.Install Redis as a service

**Download Redis**
https://github.com/tporadowski/redis/releases. Download the .zip file of the lastest vision and unzip it.

**Open Command Prompt as Administrator**
Search for CMD in the Start Menu.
Right-click on Command Prompt and select Run as Administrator.

**Navigate to the Redis Installation Directory**
Use the following command to move to the folder where Redis is installed (replace the path as needed):
```
cd C:\work\Redis
```
**Install Redis as a Service**
Run the following command to install Redis as a Windows service:
```
redis-server --service-install redis.windows-service.conf --loglevel verbose
```
**Start the Redis Service**
```
net start redis
```
---

### 4.Run the Backend Application:
Use IntelliJ IDEA to run your SpringBoot application. This can typically be done by right-clicking on the main application file and selecting `Run`.

## Frontend Setup

### 1.Install Dependencies 

Navigate to the frontend directory where the `package.json` file is located. Open a terminal in this directory and run the following command to install all necessary dependencies:
```sh
npm install
```
---

### 2.Run the Vue Project

Once the dependencies are installed, you can start the Vue.js application by running:
```sh
npm run dev
```
This command will compile and hot-reload the project for development purposes. It will provide you with a URL where the project is hosted locally, typically `http://localhost:8080/`.

## Run the Project

Ensure that both the backend and frontend services are running. The backend service should connect to MySQL successfully, and the frontend should be accessible via the browser at the provided local URL. You can then use the application through visiting the URL provided by frontend.

---

# Part3 Deployment instructions

## 1. Environment Setup

**Install Docker**  
Ensure Docker is installed and running.

**Create Docker Network：**
```bash
docker network create my-network
```
---

## 2. Redis Service Configuration and Launch

**Configure Redis Service with Environment Variables**

The application.yml file in the backend service uses environment variables to configure Redis connections:

```yaml
spring:
  redis:
    host: ${SPRING_REDIS_HOST:localhost}  # Uses SPRING_REDIS_HOST environment variable if available
    port: 6379  # Default port
```

**Steps to Set Environment Variables**

To set environment variables on **Windows**:

```powershell
$env:SPRING_REDIS_HOST="redis"
```

Verify the environment variable:

```powershell
echo $env:SPRING_REDIS_HOST
```
---

## 3. Building and Launching Docker Containers

### Deploying the Backend Service

1. **Build the backend image**
   Run the following command in the `backend` directory:
   ```bash
   docker build -t aifinance-backend .
   ```

2. **Start the backend container**
   ```bash
   docker run -d --name aifinance-backend --network my-network -p 8080:8080 aifinance-backend
   ```

### Launch Redis Service

```bash
docker run -d --name redis --network my-network redis
```

---

## 4. Nginx Service Configuration and Deployment

### Nginx Configuration File  (`nginx.conf`)
Check and modify the Nginx configuration file in the `frontend` directory, particularly the frontend resources `location` and backend `location /api/ `settings. 
Make sure to listen on the appropriate port (port 80 in this project).
Core code are a below.

```nginx
    listen       80;
    server_name  localhost;

    location / {
        root /usr/share/nginx/html;
        index index.html index.htm;
        try_files $uri /index.html;
    }  
    location /api/ {
        rewrite ^/api/(.*) /$1 break;  
        proxy_pass http://aifinance-backend:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
```

### Deploy Nginx Container

1. **Ensure the local `nginx.conf` file path is correct.**

2. **Start the Nginx container**
   ```bash
   docker run -d --name nginx --network my-network -p 81:80 \    
   -v D:/Documents/2024S2/5620/5620Project/frontend/nginx.conf:/etc/nginx/nginx.conf:ro \   
   -v D:/Documents/2024S2/5620/5620Project/frontend/dist:/usr/share/nginx/html:ro nginx
   ```
   Here，`D:/Documents/2024S2/5620/5620Project/frontend/nginx.conf` is the path to the `nginx.conf` file and
      `D:/Documents/2024S2/5620/5620Project/frontend/dist`is the path to the packaged frontend files. Modify these paths as needed.

---

## 5. Deployment Verification

**Access the frontend page**
   Open your browser and visit: `http://localhost:81`. You should be redirected to the login page, and after logging in, the system should function properly.

---

# Part4 Advanced technologies used

In the project, we used the following advanced technologies:

**Application Frameworks**
`springboot` for backend development  
`vue.js` for frontend development  

**Cloud Services**
`Google Cloud SQL` and `MySQL` for database management
`Google Clound Stroage` for file storage

**Deployment Systems** 
`Docker` for deployment  
`Nginx` as a reverse proxy server.

**Cache Database**
`redis` for JWT Token active invalidation.
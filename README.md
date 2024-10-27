# Part 1 Overview

AI Finance is an intelligent investment and financial management platform designed to provide users with personalized, data-driven financial planning and investment management services. Leveraging advanced AI algorithms and financial data analysis, the platform conducts comprehensive assessments of users' financial profiles and risk preferences, offering tailored investment strategies and savings plans to maximize financial growth while managing risk.

## Core Features

**Investment Risk Assessment:**
AI Finance evaluates users’ risk tolerance and investment preferences through a detailed questionnaire and data analysis, generating a personalized risk assessment report. This report helps users understand their risk tolerance levels in investments, allowing them to align with suitable investment strategies and products.

**Daily Savings and Investment Advice:**
Based on users' income, expenses, savings, and financial goals, AI Finance offers personalized advice for managing daily savings and investments. The platform can recommend savings and investment products, such as stocks, funds or bonds, tailored to users' risk assessment results, aiding in long-term wealth accumulation.

**Market Insights and Information Retrieval:**
AI Finance provides real-time financial market insights and information retrieval functions, covering major global financial markets. Users can access the latest market trends, real-time stock and fund prices, and essential financial news. Additionally, the platform supports in-depth information retrieval on specific companies and industries, empowering users to make informed investment decisions.

---

# Part 2 Configuration

## Backend Setup

### 1. Connect to Google Cloud SQL Instance

#### Prerequisites

Before connecting to a Google Cloud SQL instance, the following conditions must be met:

- A Cloud SQL instance running MySQL has been created on the Google Cloud Platform (GCP).
- You have provided your public IP address to the administrator, who has configured it to allow access to the Cloud SQL instance.
- MySQL Workbench or MySQL command-line tool is installed on your local machine.。

#### Obtain Your Public IP Address

To connect to the Google Cloud SQL instance, you need to find your public IP address and provide it to the administrator for access configuration.

#### Connect to Google Cloud SQL Using MySQL Workbench

1. **Open MySQL Workbench**。
2. Click the “+” button at the top left to create a new connection.
3. **Configure the connection settings**：
   - **Connection Name**：project-439505:australia-southeast1:aifinance
   - **Connection Method**：choose `Standard (TCP/IP)`。
   - **Hostname**：Enter the public IP address of the Cloud SQL instance: `35.189.27.126`.
   - **Port**：The default is 3306 (the MySQL port).
   - **Username**：Enter the username configured in the Cloud SQL instance: admin.
   - **Password**：Click “Store in Vault...” and enter the password configured in the Cloud SQL instance: 123.

4. Click Test Connection to test the connection. If the test is successful, the connection is correctly configured.
5. **Save and Connect:**：Click OK to save the connection settings, then click Connect to connect to your Cloud SQL instance.

#### Create database in Google Cloud SQL

The database schema can be found in the `5620_aifinance.sql` file, which is included in the `backend` directory.

---

#### Configure `application.yml`:
Navigate to `src/main/resources/application.yml` in your project. Update the following lines to ensure the application connects to your Google Cloud SQL instance:

```yml
spring.datasource.url=jdbc:mysql://35.189.27.126:3306/aifinance
spring.datasource.username=admin
spring.datasource.password=123
```

---

### 2. Connect to Google Cloud Storage Bucket

We have created the bucket and included the gcs credentials jsonfile in the `backend` directory. All you need is to update the following lines in the `application.yml` to ensure the application can read the json file from the correct directory.
```yml
gcs:
  credentials:
    jsonPath: D:/Documents/2024S2/5620/5620Project/backend/project-439505-7a2ea3d63268.json
```

---

### 3. Install Redis as a service

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

### 4. Run the Backend Application:
Use IntelliJ IDEA to run your SpringBoot application. This can typically be done by right-clicking on the main application file and selecting `Run`.

## Frontend Setup

### 1. Install Dependencies 

Navigate to the `frontend` directory where the `package.json` file is located. Open a terminal in this directory and run the following command to install all necessary dependencies:
```sh
npm install
```
---

### 2. Run the Vue Project

Once the dependencies are installed, you can start the Vue.js application by running:
```sh
npm run dev
```
This command will compile and hot-reload the project for development purposes. It will provide you with a URL where the project is hosted locally, typically `http://localhost:8080/`.

## Run the Project

Ensure that both the backend and frontend services are running. The backend service should connect to MySQL successfully, and the frontend should be accessible via the browser at the provided local URL. You can then use the application through visiting the URL provided by frontend.

---

# Part 3 Deployment instructions

## 1. Environment Setup

**Install Docker**  
Ensure Docker is installed and running.

**Create Docker Network：**
```bash
docker network create my-network
```
---

## 2. Redis Service Configuration 

Change the `application.yml` file in the backend service configure Redis connections:

```yml
spring:
  redis:
    host: redis  # change the host from localhost to redis
    port: 6379  # Default port
```
---

## 3. Build the project for Docker

**Backend build**
1. Open a terminal and navigate to the backend directory
2. Use Maven to build the project
```bash
   mvn clean package
```
3. After the build, the jar file will be generated in the target/ directory.

**Frontend build**
1.Open a terminal and navigate to the frontend directory
2.Once the dependencies are installed, you can build the application by running:
```bash
npm run build
```
3.The static files will be generated in the dist/ directory.

## 3. Building and Launching Docker Containers

### Deploying the Backend Service

1. **Build the backend image**
   Run the following command in the `backend` directory:
   ```bash
   docker build -t aifinance-backend .
   ```

2. **Start the backend container**
   ```bash
   docker run -d --name aifinance-backend --network my-network -p 8080:8080 -e "SPRING_PROFILES_ACTIVE=prod" aifinance-backend 
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
docker run -d --name nginx --network my-network -p 81:80 `
-v D:/Documents/2024S2/5620/5620Project/frontend/nginx.conf:/etc/nginx/nginx.conf:ro `
-v D:/Documents/2024S2/5620/5620Project/frontend/dist:/usr/share/nginx/html:ro nginx
```
   Here，`D:/Documents/2024S2/5620/5620Project/frontend/nginx.conf` is the path to the `nginx.conf` file and
      `D:/Documents/2024S2/5620/5620Project/frontend/dist`is the path to the packaged frontend files. Modify these paths as needed.

---

## 5. Deployment Verification

**Access the frontend page**
   Open your browser and visit: `http://localhost:81`. You should be redirected to the login page, and after logging in, the system should function properly.

---

# Part 4 Advanced technologies used

In the project, we used the following advanced technologies:

**Application Frameworks**
`Springboot` for backend development  
`vue.js` for frontend development  

**Cloud Services**
`Google Cloud SQL` and `MySQL` for database management
`Google Clound Stroage` for file storage

**Deployment Systems** 
`Docker` for deployment  
`Nginx` as a reverse proxy server.

**AI tools**
`OpenAI api` for generating AI contents

**Cache Database**
`redis` for JWT Token active invalidation.
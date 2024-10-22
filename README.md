# Project Setup Guide

This README provides detailed instructions on how to set up and run the website project after pulling the code. The website utilizes Java Spring Boot for the backend and Vue.js for the frontend.

## Prerequisites

Before you begin, ensure you have the following software installed:
- MySQL 
- Java (JDK) 
- Node.js
- NPM
- Redis
- IntelliJ IDEA (or any preferred Java IDE, but IDEA for backend code and VScode for frontend code is highly recommended)

## Backend Setup

### Step 1: Install MySQL

#### Download MySQL:
Visit the MySQL official website to download the MySQL Community Server for your operating system: [MySQL Downloads](https://dev.mysql.com/downloads/mysql/).

#### Install MySQL:
Follow the installation guide provided during the download process. The installation wizard will guide you through the setup.

#### Configure MySQL:
During installation, you will be prompted to set the root password. Do remember your password and make sure MySQL is set to run on the default port `3306`.

Next you will need to create a database. Inside the database, you will create the following table. 

```
-- Create database
create database 5620_aifinance;

-- Use the database
use 5620_aifinance;

-- create User table
create table user (
                      id int unsigned primary key auto_increment comment 'ID',
                      username varchar(20) not null unique comment 'username',
                      password varchar(32)  comment 'password',
                      nickname varchar(10)  default '' comment 'nickname',
                      email varchar(128) default '' comment 'email',
                      user_pic varchar(128) default '' comment 'profile picture',
                      create_time datetime not null comment 'create_time',
                      update_time datetime not null comment 'update_time'
) comment 'User table';

```
### Step 2: Connect Backend to MySQL using IntelliJ IDEA

#### Open the Project:
Open IntelliJ IDEA and import your Spring Boot project. You can do this by selecting `Open` and navigating to your project directory.

#### Configure `application.properties`:
Navigate to `src/main/resources/application.properties` in your project. Add or update the following lines to ensure the application connects to your MySQL instance:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=root
spring.datasource.password=your_mysql_password
```
Replace `your_database_name` with the name of your database and your_mysql_password with your mysql password.

### Step 3: Install Redis as a service

### Download Redis: 
https://github.com/tporadowski/redis/releases. Download the .zip file of the lastest vision and unzip it.

### Open Command Prompt as Administrator
Search for CMD in the Start Menu.
Right-click on Command Prompt and select Run as Administrator.

### Navigate to the Redis Installation Directory
Use the following command to move to the folder where Redis is installed (replace the path as needed):

```
cd C:\work\Redis
```
### Install Redis as a Service
Run the following command to install Redis as a Windows service:
```
redis-server --service-install redis.windows-service.conf --loglevel verbose
```
### Start the Redis Service
```
net start redis
```

#### Run the Application:
Use IntelliJ IDEA to run your Spring Boot application. This can typically be done by right-clicking on the main application file and selecting `Run`.

## Frontend Setup

### Step 1: Install Dependencies

Navigate to the frontend directory where the `package.json` file is located. Open a terminal in this directory and run the following command to install all necessary dependencies:

```sh
npm install
```

### Step 2: Run the Vue Project

Once the dependencies are installed, you can start the Vue.js application by running:

```sh
npm run dev
```

This command will compile and hot-reload the project for development purposes. It will provide you with a URL where the project is hosted locally, typically `http://localhost:8080/`.

## Running the Project

Ensure that both the backend and frontend services are running. The backend service should connect to MySQL successfully, and the frontend should be accessible via the browser at the provided local URL.

If you encounter any issues, please review the configurations, check the console for any errors, and ensure all dependencies are correctly installed.

Enjoy developing the website!

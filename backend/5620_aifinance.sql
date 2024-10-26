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

CREATE TABLE user_finance (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    user_id INT UNSIGNED NOT NULL COMMENT 'Associated User ID',
    balance DECIMAL(15, 2) DEFAULT 0 COMMENT 'Account Balance',
    has_loan BOOLEAN DEFAULT FALSE COMMENT 'Has Loan',
    loan_amount DECIMAL(15, 2) DEFAULT 0 COMMENT 'Loan Amount',
    monthly_salary DECIMAL(15, 2) DEFAULT 0 COMMENT 'Monthly Salary',
    savings DECIMAL(15, 2) DEFAULT 0 COMMENT 'Household Savings',
    investment_goal VARCHAR(255) DEFAULT '' COMMENT 'Investment Goal',
    risk_tolerance VARCHAR(255) DEFAULT '' COMMENT 'Risk Tolerance',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) COMMENT 'User Financial Information Table';



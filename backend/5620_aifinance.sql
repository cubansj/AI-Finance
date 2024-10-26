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

CREATE TABLE `user_finance`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` int(10) UNSIGNED NOT NULL COMMENT 'Associated User ID',
  `balance` decimal(15, 2) NULL DEFAULT 0.00 COMMENT 'Account Balance',
  `has_loan` tinyint(1) NULL DEFAULT 0 COMMENT 'Has Loan',
  `loan_amount` decimal(15, 2) NULL DEFAULT 0.00 COMMENT 'Loan Amount',
  `monthly_salary` decimal(15, 2) NULL DEFAULT 0.00 COMMENT 'Monthly Salary',
  `savings` decimal(15, 2) NULL DEFAULT 0.00 COMMENT 'Household Savings',
  `investment_goal` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Investment Goal',
  `risk_tolerance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'Risk Tolerance',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Creation Time',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Update Time',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  CONSTRAINT `user_finance_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'User Financial Information Table' ROW_FORMAT = Dynamic;

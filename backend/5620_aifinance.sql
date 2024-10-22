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
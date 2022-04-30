create database if not exists addressbook;

use addressbook;

drop table if exists contacts;

create table contacts (
id int not null auto_increment primary key,
first_name varchar(30) not null,
last_name varchar(30),
address varchar(100) not null
);
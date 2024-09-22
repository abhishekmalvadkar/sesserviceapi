create table users (

 id bigint auto_increment primary key,
 name varchar(100) not null,
 email varchar(45) not null unique,
 password varchar(100),
 phone_no varchar(20) not null,
 temp_password varchar(100),
 is_locked bit(1) not null,
 active bit(1) not null,
 delete_flag bit(1) not null ,
 created_on datetime not null,
 updated_on datetime not null,
 created_by bit(1) null,
 update_by bit(1) null
);
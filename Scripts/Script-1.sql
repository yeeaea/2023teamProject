create table ques_board(
    ques_no      bigint auto_increment PRIMARY KEY,
    ques_title   VARCHAR(200)   NOT NULL,
    ques_content VARCHAR(2000) NOT NULL,
    ques_rdate   TIMESTAMP,
    ques_udate   TIMESTAMP,
    ques_filename VARCHAR(150),
    ques_filepath VARCHAR(300),
    ques_visit bigint default 0,
    userid VARCHAR(1000),
    nickname VARCHAR(30)
);


/** 댓글 **/

drop table free_comment;
drop table ques_comment;

CREATE TABLE free_comment (
    free_cmt_no      BIGINT AUTO_INCREMENT NOT NULL,
    free_no bigint not null,
    free_cmt_content VARCHAR(300) NOT NULL, /* 데이터 값 넣고 300 맞게 적용됐는지 확인*/
    free_cmt_rdate   TIMESTAMP,
    free_cmt_udate   TIMESTAMP,
    primary key(free_cmt_no),
    foreign key(free_no) references free_board(free_no)  
); 

CREATE TABLE ques_comment (
    ques_cmt_no      BIGINT AUTO_INCREMENT NOT NULL,
    ques_no bigint not null,
    ques_cmt_content VARCHAR(300) NOT NULL, /* 데이터 값 넣고 300 맞게 적용됐는지 확인*/
    ques_cmt_rdate   TIMESTAMP,
    ques_cmt_udate   TIMESTAMP,
    primary key(ques_cmt_no),
    foreign key(ques_no) references ques_board(ques_no)  
);  


/** 시큐리티 **/
drop table if exists member CASCADE;

create table member
(
    id bigint auto_increment,
    userid varchar(255) not null,
    password varchar(255) not null,
    nickname varchar(30) NOT NULL,
    email varchar(50) not null,
    role varchar(255) not null,
    primary key (id),
    unique (userid, nickname, email)
);

insert into member(userid, nickname, password, email, role) values ('nahwasa', '관리자', '$2a$12$jcKXsj4ZAIkGgZdnUQ6EcOduMlurEtX7Szjhr.kQp2iQXNucjZMI6', 'abcd@gmail.com', 'ADMIN');
insert into member(userid, nickname, password, email, role) values ('user', '회원', '$2a$12$jcKXsj4ZAIkGgZdnUQ6EcOduMlurEtX7Szjhr.kQp2iQXNucjZMI6','dsss@gmail.com', 'USER');

-- 기존 병원, 장묘업체 테이블 삭제
drop table if exists hospital cascade;
drop table if exists funeral cascade;

-- 지도 테이블 추가
create table place (
    no    bigint auto_increment NOT NULL PRIMARY KEY,
    type  VARCHAR(50) not null,
    name  VARCHAR(100) NOT NULL,
    tel   VARCHAR(100),
    addr  VARCHAR(200) NOT NULL,
    sido  VARCHAR(100) NOT NULL,
    gugun VARCHAR(100) NOT NULL,
    time  VARCHAR(50),
    lat        DOUBLE       NOT NULL,
    lon        DOUBLE       NOT NULL
);
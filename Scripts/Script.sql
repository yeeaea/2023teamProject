-- 질문 게시판 테이블 생성
create table ques_board(
    ques_no      bigint auto_increment NOT NULL PRIMARY KEY,
    ques_title   VARCHAR(200)   NOT NULL, 
    ques_content VARCHAR(2000) NOT NULL,
    ques_rdate   TIMESTAMP,
    ques_udate   TIMESTAMP 
);

create sequence quesboard_seq start with 1 increment by 1;


-- 장묘 업체 테이블 생성
create table funeral(
    funeral_no    bigint auto_increment NOT NULL PRIMARY KEY,
    funeral_name  VARCHAR(100) NOT NULL,
    funeral_tel   VARCHAR(100),
    funeral_addr  VARCHAR(200) NOT NULL,
    funeral_time  VARCHAR(50)
);

drop table free_board;
drop table ques_board;
-- 자유게시판 정보 테이블 생성
create table free_board(
    free_no      bigint auto_increment NOT NULL PRIMARY KEY,
    free_title   VARCHAR(200)   NOT NULL, 
    free_content VARCHAR(2000) NOT NULL,
    free_rdate   TIMESTAMP ,
    free_udate   TIMESTAMP
    );
-- 질문 게시판 테이블 생성
create table ques_board(
    ques_no      bigint auto_increment NOT NULL PRIMARY KEY,
    ques_title   VARCHAR(200)   NOT NULL, 
    ques_content VARCHAR(2000) NOT NULL,
    ques_rdate   TIMESTAMP,
    ques_udate   TIMESTAMP 
);

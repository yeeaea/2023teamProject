
/* 기존 병원, 장묘업체 테이블 삭제 */
drop table if exists hospital cascade;
drop table if exists funeral cascade;

/* 지도 테이블 추가 */
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

/* 게시판 테이블 생성*/
create table free_board(
    free_no      bigint auto_increment NOT NULL PRIMARY KEY,
    free_title   VARCHAR(200)   NOT NULL,
    free_content TEXT NOT NULL,
    free_rdate   TIMESTAMP,
    free_udate   TIMESTAMP,
    free_filename VARCHAR(150),
    free_filepath VARCHAR(300),
    free_visit bigint default 0,
    userid VARCHAR(1000),
    nickname VARCHAR(30)
);

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

/* 댓글 테이블 */
CREATE TABLE free_comment (
    free_cmt_no      BIGINT AUTO_INCREMENT NOT NULL,
    free_no bigint not null,
    free_cmt_content VARCHAR(300) NOT NULL, /* 데이터 값 넣고 300 맞게 적용됐는지 확인 */
    free_cmt_rdate   TIMESTAMP,
    free_cmt_udate   TIMESTAMP,
    userid varchar(255),
    nickname varchar(30),
    primary key(free_cmt_no),
    foreign key(free_no) references free_board(free_no)
); 

CREATE TABLE ques_comment (
    ques_cmt_no      BIGINT AUTO_INCREMENT NOT NULL,
    ques_no bigint not null,
    ques_cmt_content VARCHAR(300) NOT NULL, /* 데이터 값 넣고 300 맞게 적용됐는지 확인 */
    ques_cmt_rdate   TIMESTAMP,
    ques_cmt_udate   TIMESTAMP,
    userid varchar(255),
    nickname varchar(30),
    primary key(ques_cmt_no),
    foreign key(ques_no) references ques_board(ques_no)
);
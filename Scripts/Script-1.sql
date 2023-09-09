create table ques_board(
    ques_no      bigint auto_increment PRIMARY KEY,
    ques_title   VARCHAR(200)   NOT NULL,
    ques_content VARCHAR(2000) NOT NULL,
    ques_rdate   TIMESTAMP,
    ques_udate   TIMESTAMP,
    ques_filename VARCHAR(150),
    ques_filepath VARCHAR(300),
    ques_visit bigint default 0
);


/** 댓글 **/

CREATE TABLE ques_comment (
    ques_cmt_no BIGINT AUTO_INCREMENT PRIMARY KEY,
    ques_cmt_content VARCHAR(1000) NOT NULL,
    ques_cmt_rdate DATETIME,
    ques_cmt_udate DATETIME,
    ques_no bigint(20),
    mem_id bigint(20),
    FOREIGN KEY (ques_no) REFERENCES ques_board(ques_no),
    FOREIGN KEY (mem_id) REFERENCES member(id)
);

INSERT INTO ques_comment (mem_id, ques_cmt_rdate, ques_cmt_udate, ques_cmt_content)
VALUES
    (1, NOW(), NOW(), '강쥐가 너무 귀여워요'),
    (2, NOW(), NOW(), '역시 개팔자가 상팔자네요 ^^*~~~'), 
    (3, NOW(), NOW(), '저도 침대에만 누워있고 싶다는...');

use petconnect;

drop table ques_board;
drop table ques_comment;

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
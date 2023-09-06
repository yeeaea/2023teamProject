drop table if exists member CASCADE;

create table member
(
    id bigint auto_increment,
    userid varchar(255) not null,
    nickname varchar(30) NOT NULL, 
    password varchar(255) not null,
    roles varchar(255) not null,
    primary key (id),
    unique (userid)
);

insert into member(userid, nickname, pw, roles) values ('nahwasa', '관리자', '$2a$12$jcKXsj4ZAIkGgZdnUQ6EcOduMlurEtX7Szjhr.kQp2iQXNucjZMI6', 'ADMIN');
insert into member(userid, nickname, pw, roles) values ('user', '회원', '$2a$12$jcKXsj4ZAIkGgZdnUQ6EcOduMlurEtX7Szjhr.kQp2iQXNucjZMI6', 'USER');
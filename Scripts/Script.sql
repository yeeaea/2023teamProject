INSERT INTO QUES_BOARD (ques_NO, ques_title, ques_content, ques_rdate, ques_udate, ques_VIEWS, mem_id, ADMIN_ID)
    VALUES (petuser.petuser_seq.nextval , '제목1', '내용1', SYSDATE, SYSDATE, 23, 'member1', 'admin1');

INSERT INTO QUES_BOARD (ques_NO, ques_title, ques_content, ques_rdate, ques_udate, ques_VIEWS, mem_id, ADMIN_ID)
    VALUES (petuser.petuser_seq.nextval , '제목1', '내용1', SYSDATE, SYSDATE, 23, 'member1', 'admin1');   
    
   CREATE SEQUENCE PETuser.PETUSER_SEQ;
   
SELECT * FROM USER_SEQUENCES;
  SELECT 
   petuser_seq.nextval 
   FROM 
     dual;
     
    
    create table ques_board(
    ques_no      NUMBER         NOT NULL PRIMARY KEY,
    ques_title   VARCHAR2(200)   NOT NULL, 
    ques_content VARCHAR2(2000) NOT NULL,
    ques_rdate   TIMESTAMP,
    ques_udate   TIMESTAMP
    );
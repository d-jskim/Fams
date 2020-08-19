--sequence 생성 전 삭제
DROP SEQUENCE member_seq;

--MemberId를 위한 Sequence 생성
CREATE SEQUENCE member_seq
 START WITH     1
 INCREMENT BY   1
 NOMAXVALUE
 NOCACHE
 NOCYCLE;
 
 --Member 테이블 삭제하기 
 DROP TABLE member;
 
 --Member 테이블 생성하기
 CREATE TABLE member(
 	memberNo number PRIMARY KEY,
 	memberId varchar2(20) NOT NULL,
 	password varchar2(20) NOT NULL,
 	familyId number, --family_seq
 	nickname varchar2(20) NOT NULL,
 	indate date DEFAULT sysdate
 );

--회원가입 sample data
INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'apeach@famtodo.com','apeach', 10, '어피치');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'con@famtodo.com','con', 10, '콘');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'ryan@famtodo.com','ryan', 10, '라이언');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'frodo@famtodo.com','frodo', 20, '프로도');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'jay@famtodo.com','jay', 20, '제이');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'neo@famtodo.com','neo', 20, '네오');

select * from member;

 --familyId(테스트용) 
--TODO: [Family] 테이블 생성 시 table_seq.nextVal을 알려주고 user가 방 생성 OK 클릭하면 family데이터 삽입
--familyId, indate 제외한 컬럼 값 insert > 나중에 FAMILY 테이블과 [가족 그룹 만들기] 기능 넣어서 familyNo 부여하는 기능 있을 경우 사용
INSERT INTO member(memberNo, memberId, password, nickname) VALUES 
(member_seq.nextVal,'abc@gmail.com','abc', '무지');





--회원가입
CREATE OR REPLACE PROCEDURE member_insert(
	v_memberId IN member.memberId%TYPE,
	v_password IN member.password%TYPE,
	v_familyId IN member.familyId%TYPE,
	v_nickname IN member.nickname%TYPE)
IS
BEGIN
INSERT INTO member(memberNo, memberId, password, nickname) 
VALUES(member_seq.nextVal, v_memberId, v_password, v_nickname);
END;
/

--로그인 확인
CREATE OR REPLACE FUNCTION MEMBER_LOGIN(VID IN MEMBER.MEMBERID%TYPE,
VPW IN MEMBER.PASSWORD%TYPE) RETURN NUMBER
IS
DBPW MEMBER.PASSWORD%TYPE := NULL;
EXISTS_NUM NUMBER := 0;

BEGIN
 
SELECT CASE 
WHEN EXISTS (SELECT MEMBERID FROM MEMBER WHERE MEMBER.MEMBERID = VID)  THEN 1
ELSE 0 
END 
INTO EXISTS_NUM FROM DUAL;

IF EXISTS_NUM = 1 THEN  
SELECT PASSWORD INTO DBPW
FROM MEMBER
WHERE MEMBERID = VID;
IF DBPW = VPW THEN
RETURN 1;
ELSE
RETURN 0;
END IF;
ELSIF EXISTS_NUM = 0 THEN
RETURN 0;
END IF;
END;
/

-- 로그인 성공 시 해당 회원의 모든 정보 다 받아오기 (nickname 필요)

CREATE OR REPLACE PROCEDURE member_selectVO(v_memberId IN member.memberId%TYPE, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR
			SELECT memberNo, memberId, familyId, nickname FROM member WHERE memberId = v_memberId;
END;
/

SQL> var res refcursor;
SQL> exec member_selectVO('apeach@famtodo.com', :res)

-- 현재 user와 familyId가 같은 가족 멤버들 불러오기
CREATE OR REPLACE PROCEDURE member_memberList(v_familyId IN member.familyId%TYPE, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR
			SELECT nickname FROM member WHERE familyId = v_familyId;
END;
/

SQL> var res refcursor;
SQL> exec member_memberList(10, :res)
SQL> print res
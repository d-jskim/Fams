--sequence ���� �� ����
DROP SEQUENCE member_seq;

--MemberId�� ���� Sequence ����
CREATE SEQUENCE member_seq
 START WITH     1
 INCREMENT BY   1
 NOMAXVALUE
 NOCACHE
 NOCYCLE;
 
 --Member ���̺� �����ϱ� 
 DROP TABLE member;
 
 --Member ���̺� �����ϱ�
 CREATE TABLE member(
 	memberNo number PRIMARY KEY,
 	memberId varchar2(20) NOT NULL,
 	password varchar2(20) NOT NULL,
 	familyId number, --family_seq
 	nickname varchar2(20) NOT NULL,
 	indate date DEFAULT sysdate
 );

--ȸ������ sample data
INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'apeach@famtodo.com','apeach', 10, '����ġ');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'con@famtodo.com','con', 10, '��');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'ryan@famtodo.com','ryan', 10, '���̾�');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'frodo@famtodo.com','frodo', 20, '���ε�');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'jay@famtodo.com','jay', 20, '����');

INSERT INTO member(memberNo, memberId, password, familyId, nickname) VALUES 
(member_seq.nextVal,'neo@famtodo.com','neo', 20, '�׿�');

select * from member;

 --familyId(�׽�Ʈ��) 
--TODO: [Family] ���̺� ���� �� table_seq.nextVal�� �˷��ְ� user�� �� ���� OK Ŭ���ϸ� family������ ����
--familyId, indate ������ �÷� �� insert > ���߿� FAMILY ���̺�� [���� �׷� �����] ��� �־ familyNo �ο��ϴ� ��� ���� ��� ���
INSERT INTO member(memberNo, memberId, password, nickname) VALUES 
(member_seq.nextVal,'abc@gmail.com','abc', '����');





--ȸ������
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

--�α��� Ȯ��
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

-- �α��� ���� �� �ش� ȸ���� ��� ���� �� �޾ƿ��� (nickname �ʿ�)

CREATE OR REPLACE PROCEDURE member_selectVO(v_memberId IN member.memberId%TYPE, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR
			SELECT memberNo, memberId, familyId, nickname FROM member WHERE memberId = v_memberId;
END;
/

SQL> var res refcursor;
SQL> exec member_selectVO('apeach@famtodo.com', :res)

-- ���� user�� familyId�� ���� ���� ����� �ҷ�����
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
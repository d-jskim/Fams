--Task sequence �����ϱ�
DROP SEQUENCE task_seq;

--TaskNo�� ���� Sequence �����ϱ�
CREATE SEQUENCE task_seq
increment by 1
start with 1;

--Task ���̺� �����ϱ�
DROP TABLE task;

--Task ���̺� �����ϱ�
CREATE TABLE TASK(
	taskNo number primary key,
	category varchar2(30),
	task varchar2(50) Not Null,	
	status varchar2(30),
	familyId number,
	member varchar2(20) Not Null, -- nickname
	substitute varchar2(20),
	startDate date DEFAULT sysdate,
	endDate date DEFAULT sysdate, 
	memo varchar2(100),
	LikeNo number
);

--Sample Data for To do list test


--TASK ���̺� ���� 
--DESC task; //DESC��ɹ��� sqlplus������ ���� ������
SQL> desc task;



--Sample data for task (to do list)
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��û��','�ξ� ����', 'In progress', '����ġ', 10, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��û��','�и�����', 'Completed', '��', 10, '2020/06/12', '2020/06/13');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��û��','ȭ��� û��', 'In progress', '���̾�', 10, '2020/06/13', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��û��','â�� �۱�', 'In progress', '���ε�', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��û��','â�� ����', 'In progress', '����', 20, '2020/06/12', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'�庸��','�庸��', 'Not started', '����', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'����Ļ�','�丮', 'Not started', '���ε�', 20, '2020/06/15', '2020/06/16');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'����Ļ�','������', 'Not started', '�׿�', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��ħ�Ļ�','�丮', 'Completed', '����', 20, '2020/06/13', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'��ħ�Ļ�','������', 'In progress', '�׿�', 20);
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'û��','�и�����', 'In progress', '����', 20, '2020/06/13', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'�ݷ�����','��å', 'Not started', '����ġ', 10, '2020/06/15', '2020/06/16');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'�ݷ�����','���ֱ�', 'Completed', '����ġ', 10, '2020/06/15', '2020/06/16');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'����','��Ź�� ���', 'Not started', '����', 20, '2020/06/16', '2020/06/17');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'����','���� �α�', 'Not started', '�׿�', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'����','�ٸ���', 'Not started', '����', 20, '2020/06/16', '2020/06/17');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'�庸��','�庸��', 'Completed', '�׿�', 20, '2020/06/13', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'�ݷ�����','����Ű��', 'Completed', '��', 10, '2020/06/13', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'û��','ȭ��� û��', 'Completed', '���̾�', 10, '2020/06/13', '2020/06/14');

--!!!!!!!!!�ݵ�� Ŀ���ؾ� ��...java�� ����Ŭ�� autocommit(false)�����߰�, ������Ʈ�� DML�� �ڵ�Ŀ�� �� ��.
commit;
--������ Ȯ��
SELECT * FROM task;

--Insert All
CREATE OR REPLACE PROCEDURE task_insertAll(v_category in task.category%type,
										v_task in task.task%type, 
										v_status in task.status%type,
										v_familyId in task.familyId%type,
										v_member in task.member%type,
										v_substitute in task.substitute%type,
										v_startDate in varchar2,
										v_endDate in varchar2,
										v_memo in task.memo%type)
IS
BEGIN
	INSERT INTO Task(taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo) 
	VALUES(task_seq.nextval, v_category, v_task, v_status, v_familyId, v_member, v_substitute, v_startDate, v_endDate, v_memo);
END;
/
--Ȯ��
SQL> exec task_insertAll('������Ʈ', '�����͸𵨸�', 'Not Started', 10, '����ġ', '', '2020-08-20', '2020-08-20', '' )
exec task_insert('�Ž� �ٴ� �۱�', 'û���ϱ�', '����')
SELECT * FROM task;

--Task ���� 
CREATE OR REPLACE PROCEDURE task_delete(my_taskno in task.taskNo%type)
IS
BEGIN
	DELETE FROM task WHERE taskNo = my_taskno;
END;
/

--SELECT ��� ������ -> ���̺�信 ǥ�� 
CREATE OR REPLACE PROCEDURE task_selectAll (v_familyId IN task.familyId%type, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR SELECT taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo, likeNo 
	FROM task WHERE familyId = v_familyId;
END;
/

SQL> var res refcursor;
SQL> exec task_selectAll(10, :res)
-- ������ Ÿ�� Ȯ�� from Oracle: 2020-06-09 10:55:05.0 -- 
select sysdate from task;

--My To Do List ����
CREATE OR REPLACE PROCEDURE task_selectMyList (v_familyId IN task.familyId%type, v_nickname IN task.member%type, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR SELECT taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo, likeNo 
	FROM task WHERE familyId = v_familyId AND member = v_nickname;
END;
/

--Task ����
CREATE OR REPLACE PROCEDURE task_update(v_category in task.category%type,
										v_task in task.task%type, 
										v_status in task.status%type,
										v_familyId in task.familyId%type,
										v_member in task.member%type,
										v_substitute in task.substitute%type,
										v_startDate in varchar2,
										v_endDate in varchar2,
										v_memo in task.memo%type,
										v_likeNo in task.likeNo%type,
										v_taskNo in task.taskNo%type
										)
IS
BEGIN
	UPDATE task SET category = v_category, task = v_task, status = v_status, familyId = v_familyId, member = v_member,
					substitute = v_substitute, startDate = TO_DATE(v_startDate), endDate = TO_DATE(v_endDate),
					memo = v_memo, likeNo = v_likeNo
	WHERE taskNo = v_taskNo;
END;
/

SQL> exec task_update('��û��', 'ȭ���û��', 'In progress', 10, '��', '', '2020-08-11', '2020-08-12', '', 0, 3)

--Like�� ����

CREATE OR REPLACE PROCEDURE task_updateLike(v_likeNo in task.likeNo%type, v_taskNo in task.taskNo%type)
IS
BEGIN
	UPDATE task SET likeNo = v_likeNo
	WHERE taskNo = v_taskNo;
END;
/

select * from task where familyId = 10;
SELECT taskNo, task, category, status, familyId, member, substitute, startDate, endDate, memo, likeNo 
	FROM task WHERE familyId = 10;


--����Ʈ ����
--���������� �̸� �ҷ�����

CREATE OR REPLACE PROCEDURE member_list(VFID IN TASK.FAMILYID%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT DISTINCT MEMBER FROM TASK
WHERE FAMILYID = VFID;
END;
/

--������������ ������ ��ȸ
CREATE OR REPLACE PROCEDURE task_per_member(VFID IN TASK.FAMILYID%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT MEMBER, COUNT(TASK)
FROM TASK
WHERE FAMILYID = VFID
GROUP BY MEMBER;
END;
/

--status�� ���� ��ȸ
CREATE OR REPLACE PROCEDURE task_status(MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT STATUS, COUNT(STATUS)
FROM TASK
GROUP BY STATUS;
END;
/


--���κ� ������ ���� ��ȸ
CREATE OR REPLACE PROCEDURE individual_task(VFID IN TASK.FAMILYID%TYPE, VNAME IN TASK.MEMBER%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT TASK, COUNT(TASK)
FROM TASK
WHERE MEMBER = VNAME and FAMILYID = VFID
GROUP BY TASK;
END;
/

--����Ʈ4
--��� ī�װ� ��ȸ -> stackedbarchart x�࿡ ����
CREATE OR REPLACE PROCEDURE select_all_task(VFID IN TASK.FAMILYID%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT DISTINCT TASK FROM TASK
WHERE FAMILYID = VFID;
END;
/
--�����Ϻ� ���� ���� ��ȸ
CREATE OR REPLACE PROCEDURE individual_task_reverse(VTASK IN TASK.TASK%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT MEMBER, COUNT(MEMBER)
FROM TASK
WHERE TASK = VTASK
GROUP BY MEMBER;
END;
/

--�ð� ��ȭ�� ���� ���κ� ������ ���� (end date����)
CREATE OR REPLACE PROCEDURE time_series_task(VNAME IN TASK.MEMBER%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT ENDDATE, COUNT(TASK)
FROM TASK
WHERE MEMBER = VNAME
GROUP BY ENDDATE;
END;
/



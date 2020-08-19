--Task sequence 삭제하기
DROP SEQUENCE task_seq;

--TaskNo를 위한 Sequence 생성하기
CREATE SEQUENCE task_seq
increment by 1
start with 1;

--Task 테이블 삭제하기
DROP TABLE task;

--Task 테이블 생성하기
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


--TASK 테이블 구조 
--DESC task; //DESC명령문은 sqlplus에서만 실행 가능함
SQL> desc task;



--Sample data for task (to do list)
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'대청소','부엌 정리', 'In progress', '어피치', 10, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'대청소','분리수거', 'Completed', '콘', 10, '2020/06/12', '2020/06/13');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'대청소','화장실 청소', 'In progress', '라이언', 10, '2020/06/13', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'대청소','창문 닦기', 'In progress', '프로도', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'대청소','창고 정리', 'In progress', '제이', 20, '2020/06/12', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'장보기','장보기', 'Not started', '제이', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'저녁식사','요리', 'Not started', '프로도', 20, '2020/06/15', '2020/06/16');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'저녁식사','설거지', 'Not started', '네오', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'아침식사','요리', 'Completed', '제이', 20, '2020/06/13', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'아침식사','설거지', 'In progress', '네오', 20);
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'청소','분리수거', 'In progress', '제이', 20, '2020/06/13', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'반려동물','산책', 'Not started', '어피치', 10, '2020/06/15', '2020/06/16');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'반려동물','밥주기', 'Completed', '어피치', 10, '2020/06/15', '2020/06/16');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'빨래','세탁기 사용', 'Not started', '제이', 20, '2020/06/16', '2020/06/17');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'빨래','빨래 널기', 'Not started', '네오', 20, '2020/06/14', '2020/06/15');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'빨래','다림질', 'Not started', '제이', 20, '2020/06/16', '2020/06/17');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'장보기','장보기', 'Completed', '네오', 20, '2020/06/13', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'반려동물','목욕시키기', 'Completed', '콘', 10, '2020/06/13', '2020/06/14');
INSERT INTO task(taskNo, category, task, status, member, familyId, startDate, endDate) VALUES 
(task_seq.nextVal,'청소','화장실 청소', 'Completed', '라이언', 10, '2020/06/13', '2020/06/14');

--!!!!!!!!!반드시 커밋해야 함...java의 오라클은 autocommit(false)설정했고, 프롬프트는 DML은 자동커밋 안 함.
commit;
--데이터 확인
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
--확인
SQL> exec task_insertAll('프로젝트', '데이터모델링', 'Not Started', 10, '어피치', '', '2020-08-20', '2020-08-20', '' )
exec task_insert('거실 바닥 닦기', '청소하기', '무지')
SELECT * FROM task;

--Task 삭제 
CREATE OR REPLACE PROCEDURE task_delete(my_taskno in task.taskNo%type)
IS
BEGIN
	DELETE FROM task WHERE taskNo = my_taskno;
END;
/

--SELECT 모든 데이터 -> 테이블뷰에 표시 
CREATE OR REPLACE PROCEDURE task_selectAll (v_familyId IN task.familyId%type, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR SELECT taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo, likeNo 
	FROM task WHERE familyId = v_familyId;
END;
/

SQL> var res refcursor;
SQL> exec task_selectAll(10, :res)
-- 데이터 타입 확인 from Oracle: 2020-06-09 10:55:05.0 -- 
select sysdate from task;

--My To Do List 보기
CREATE OR REPLACE PROCEDURE task_selectMyList (v_familyId IN task.familyId%type, v_nickname IN task.member%type, res out SYS_REFCURSOR)
IS
BEGIN
	OPEN res FOR SELECT taskNo, category, task, status, familyId, member, substitute, startDate, endDate, memo, likeNo 
	FROM task WHERE familyId = v_familyId AND member = v_nickname;
END;
/

--Task 수정
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

SQL> exec task_update('대청소', '화장실청소', 'In progress', 10, '콘', '', '2020-08-11', '2020-08-12', '', 0, 3)

--Like수 증가

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


--바차트 관련
--가족구성원 이름 불러오기

CREATE OR REPLACE PROCEDURE member_list(VFID IN TASK.FAMILYID%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT DISTINCT MEMBER FROM TASK
WHERE FAMILYID = VFID;
END;
/

--가족구성원별 집안일 조회
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

--status별 개수 조회
CREATE OR REPLACE PROCEDURE task_status(MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT STATUS, COUNT(STATUS)
FROM TASK
GROUP BY STATUS;
END;
/


--개인별 집안일 개수 조회
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

--바차트4
--모든 카테고리 조회 -> stackedbarchart x축에 지정
CREATE OR REPLACE PROCEDURE select_all_task(VFID IN TASK.FAMILYID%TYPE, MYRES OUT SYS_REFCURSOR)
IS
BEGIN
OPEN MYRES FOR
SELECT DISTINCT TASK FROM TASK
WHERE FAMILYID = VFID;
END;
/
--집안일별 개인 개수 조회
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

--시간 변화에 따른 개인별 집안일 개수 (end date기준)
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



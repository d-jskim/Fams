package com.fam.DAO;

import static common.JDBCTemplate.Close;
import static common.JDBCTemplate.Commit;
import static common.JDBCTemplate.Rollback;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fam.VO.Task;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class TaskDAO implements TaskSql {

	private Connection conn;

	public TaskDAO(Connection conn) {
		this.conn = conn;
	}

	public ArrayList<Task> task_selectAll(int familyId) {

		Task vo = null;
		ArrayList<Task> all = new ArrayList<>();

		CallableStatement cstmt = null;

		ResultSet rs = null;

		try {

			cstmt = conn.prepareCall(task_selectAll);
			cstmt.setInt(1, familyId);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();

			rs = ((OracleCallableStatement) cstmt).getCursor(2); // 커서가 갖고 있는 것을 리턴받음
			// taskNo, category, task, status, familyId, member, substitute, startDate,
			// endDate, memo, likeNo
			while (rs.next()) {
				vo = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11));
				// List
				all.add(vo);
			}

		} catch (Exception e) {
			System.out.println("task_selectAll(int familyId) errors: ");
			e.printStackTrace();
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return all;
	}// end method

	public ArrayList<Task> task_selectMyList(int familyId, String nickname) {
		Task vo = null;
		ArrayList<Task> all = new ArrayList<>();

		CallableStatement cstmt = null;

		ResultSet rs = null;

		try {

			cstmt = conn.prepareCall(task_selectMyList);
			cstmt.setInt(1, familyId);
			cstmt.setString(2, nickname);
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.execute();

			rs = ((OracleCallableStatement) cstmt).getCursor(3); // 커서가 갖고 있는 것을 리턴받음
			
			while (rs.next()) {
				vo = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11));
				// List
				all.add(vo);
			}

		} catch (Exception e) {
			System.out.println("task_selectMyList(int familyId, String nickname) errors: ");
			e.printStackTrace();
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return all;
	}// end method



	public ArrayList<Task> task_insertMyList(int familyId, String nickname) {
		Task vo = null;
		ArrayList<Task> all = new ArrayList<>();

		CallableStatement cstmt = null;

		ResultSet rs = null;

		try {

			cstmt = conn.prepareCall(task_selectMyList);
			cstmt.setInt(1, familyId);
			cstmt.setString(2, nickname);
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);
			cstmt.execute();

			rs = ((OracleCallableStatement) cstmt).getCursor(3); // 커서가 갖고 있는 것을 리턴받음

			while (rs.next()) {
				vo = new Task(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5),
						rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),
						rs.getInt(11));
				// List
				all.add(vo);
			}

		} catch (Exception e) {
			System.out.println("task_selectMyList(int familyId, String nickname) errors: ");
			e.printStackTrace();
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return all;
	}

	public int task_insertAll(Task vo) {

		int res = 0; // 결과값리턴하는 변수 초기화
		CallableStatement cstmt = null;

		try {
			cstmt = conn.prepareCall(task_insertAll); // "{call task_insert(?, ?, ?....?, ?)}"; ? 9개
			// v_category, v_task, v_status, v_familyId, v_member, v_substitute,
			// v_startDate, v_endDate, v_memo
			cstmt.setString(1, vo.getCategory());
			cstmt.setString(2, vo.getTask());
			cstmt.setString(3, vo.getStatus());
			cstmt.setInt(4, vo.getFamilyId());
			cstmt.setString(5, vo.getMember());
			cstmt.setString(6, vo.getSubstitute());
			cstmt.setString(7, vo.getStartDateString());
			cstmt.setString(8, vo.getEndDateString());
			cstmt.setString(9, vo.getMemo());

			// 쿼리를 실행한다.
			cstmt.execute();
			Commit(conn);

			res = 1;

		} catch (Exception e) {
			System.out.println(e);
			try {
				Rollback(conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
				Close(cstmt);
			}
		}
		return res;
	}

	public void task_delete(int taskNo) {
		CallableStatement cstmt = null;

		try {
			cstmt = conn.prepareCall(task_delete); // "{call task_delete(?)}";
			// ?: taskNo
			cstmt.setInt(1, taskNo);

			// 쿼리를 실행한다.
			cstmt.execute();
			Commit(conn);

		} catch (Exception e) {
			System.out.println(e);
			try {
				Rollback(conn);
			} catch (Exception e2) {
				e2.printStackTrace();
			} finally {
				Close(cstmt);
			}
		}

	}

	public int task_update(Task vo) {
		System.out.println(vo.toString());
		int res = 0;
		CallableStatement cstmt = null;
		try {

			// 쿼리
			cstmt = conn.prepareCall(task_update);
			// category = v_category, task = v_task, status = v_status
			cstmt.setString(1, vo.getCategory());
			cstmt.setString(2, vo.getTask());
			cstmt.setString(3, vo.getStatus());
			cstmt.setInt(4, vo.getFamilyId());
			cstmt.setString(5, vo.getMember());
			cstmt.setString(6, vo.getSubstitute());
			cstmt.setString(7, vo.getStartDateString());
			cstmt.setString(8, vo.getEndDateString());
			cstmt.setString(9, vo.getMemo());
			cstmt.setInt(10, vo.getLikeNo());
			cstmt.setInt(11, vo.getTaskNo());

			// 쿼리 실행
			cstmt.execute();
			Commit(conn);
			res = 1;

		} catch (Exception e) {
			System.out.println(e);
			Rollback(conn);
		} // end outer try

		return res;
	}// end method

	public int task_updateLike(int likeNo, int taskNo) {
		int res = 0;
		CallableStatement cstmt = null;
		try {
			cstmt = conn.prepareCall(task_updateLike);
			cstmt.setInt(9, likeNo);
			cstmt.setInt(10, taskNo);

			// 쿼리 실행
			cstmt.execute();
			Commit(conn);
			res = 1;
		} catch (Exception e) {
			System.out.println(e);
			Rollback(conn);
		} // end outer try

		return res;
	}

	// 바차트
	// 가족 구성원 이름 리스트
	public List<String> member_list(int vfamilyid) {
		String memberName;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		List<String> nameList = new ArrayList<>();
		try {
			cstmt = conn.prepareCall(member_list);
			cstmt.setInt(1, vfamilyid);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(2);
			while (rs.next()) {
				memberName = rs.getString(1);
				nameList.add(memberName);
			}

		} catch (Exception e) {
			System.out.println("member_list" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return nameList;
	}// end method

	// 가족 구성원별 집안일 카운트
	public Map<String, Integer> count_task(int famID) {
		String memberName = null;
		int c_task = 0;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Map<String, Integer> countList = new HashMap<String, Integer>();
		try {
			cstmt = conn.prepareCall(task_per_member);
			cstmt.setInt(1, famID);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(2);
			while (rs.next()) {
				memberName = rs.getString(1);
				c_task = rs.getInt(2);
				countList.put(memberName, c_task);
			}

		} catch (Exception e) {
			System.out.println("task_count" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return countList;
	}// end method

	// 상태별 카운트
	public Map<String, Integer> count_status() {
		String status_name = null;
		int c_task = 0;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Map<String, Integer> statusList = new HashMap<String, Integer>();
		try {
			cstmt = conn.prepareCall(task_status);
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(1);
			while (rs.next()) {
				status_name = rs.getString(1);
				c_task = rs.getInt(2);
				statusList.put(status_name, c_task);
			}

		} catch (Exception e) {
			System.out.println("status_count" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return statusList;
	}// end method

	// choice box 누르면 나오는 바 차트 구현하기
	public Map<String, Integer> individual_task(int famID, String choiceName) {
		String task_name = null;
		int task_count = 0;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Map<String, Integer> taskCountList = new HashMap<String, Integer>();
		try {
			cstmt = conn.prepareCall(individual_task);
			cstmt.setInt(1, famID);
			cstmt.setString(2, choiceName);
			cstmt.registerOutParameter(3, OracleTypes.CURSOR);

			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(3);
			while (rs.next()) {
				task_name = rs.getString(1);
				task_count = rs.getInt(2);
				taskCountList.put(task_name, task_count);
			}

		} catch (Exception e) {
			System.out.println("individual_task" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return taskCountList;
	}// end method

	public List<String> select_all_task(int famID) {
		String taskName;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		List<String> taskList = new ArrayList<>();
		try {
			cstmt = conn.prepareCall(select_all_task);
			cstmt.setInt(1, famID);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(2);
			while (rs.next()) {
				taskName = rs.getString(1);
				taskList.add(taskName);
			}

		} catch (Exception e) {
			System.out.println("taskList" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return taskList;
	}

	public Map<String, Integer> individual_task_reverse(String tname) {
		String mName = null;
		int c_task2 = 0;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Map<String, Integer> taskReverseList = new HashMap<String, Integer>();
		try {
			cstmt = conn.prepareCall(individual_task_reverse);
			cstmt.setString(1, tname);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(2);
			while (rs.next()) {
				mName = rs.getString(1);
				c_task2 = rs.getInt(2);
				taskReverseList.put(mName, c_task2);
			}

		} catch (Exception e) {
			System.out.println("taskReverseList" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return taskReverseList;
	}// end method

	public Map<String, Integer> time_series_task(String memberName) {
		String dt = null;
		int c_task3 = 0;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Map<String, Integer> timeSeriesList = new HashMap<String, Integer>();
		try {
			cstmt = conn.prepareCall(time_series_task);
			cstmt.setString(1, memberName);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();
			rs = ((OracleCallableStatement) cstmt).getCursor(2);
			while (rs.next()) {
				dt = rs.getString(1);
				c_task3 = rs.getInt(2);
				timeSeriesList.put(dt, c_task3);
			}

		} catch (Exception e) {
			System.out.println("timeSeriesList" + e);
		} finally {
			Close(cstmt);
			Close(rs);
		} // end catch
		return timeSeriesList;
	}// end method

}

package com.fam.BIZ;

import static common.JDBCTemplate.Close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fam.DAO.TaskDAO;
import com.fam.VO.Task;

public class TaskBIZ {

	public int task_insertAll(Task vo) {
		Connection conn = getConnection();
		int res = new TaskDAO(conn).task_insertAll(vo);
		Close(conn);

		return res;
	}

	public ArrayList<Task> task_selectAll(int familyId) {

		Connection conn = getConnection();
		ArrayList<Task> all = new TaskDAO(conn).task_selectAll(familyId);
		Close(conn);

		return all;
	}

	public List<Task> task_selectMyList(int familyId, String nickname) {

		Connection conn = getConnection();
		ArrayList<Task> all = new TaskDAO(conn).task_selectMyList(familyId, nickname);
		Close(conn);

		return all;
	}

	public void task_delete(int delTaskNo) {
		Connection conn = getConnection();

		new TaskDAO(conn).task_delete(delTaskNo);
		// close
		Close(conn);
	}

	public int task_update(Task vo) {
		int res = 0;
		Connection conn = getConnection();

		res = new TaskDAO(conn).task_update(vo);
		// close
		Close(conn);
		return res;
	}

	public int task_updateLike(int likeNo, int taskNo) {
		int res = 0;
		Connection conn = getConnection();
		res = new TaskDAO(conn).task_updateLike(likeNo, taskNo);
		Close(conn);
		return res;
	}

	// 분석관련 메소드들
	public List<String> member_list(int vfamilyid)  {
			Connection conn = getConnection();
			List<String> nameList = new TaskDAO(conn).member_list(vfamilyid);
			Close(conn);
			return nameList;
		}

	public Map<String, Integer> count_task(int famID){
			Connection conn = getConnection();
			Map<String, Integer> countList = new TaskDAO(conn).count_task(famID);
			Close(conn);
			return countList;
		}

	public Map<String, Integer> count_status(){
			Connection conn = getConnection();
			Map<String, Integer> statusList = new TaskDAO(conn).count_status();
			Close(conn);
			return statusList;
		}

	public Map<String, Integer> individual_task(int famID, String choiceName) {
		Connection conn = getConnection();
		Map<String, Integer> taskCountList = new TaskDAO(conn).individual_task(famID, choiceName);
		Close(conn);
		return taskCountList;
	}

	public List<String> select_all_task(int famID) {
		Connection conn = getConnection();
		List<String> taskList = new TaskDAO(conn).select_all_task(famID);
		Close(conn);
		return taskList;
	}

	public Map<String, Integer> individual_task_reverse(String tname) {
		Connection conn = getConnection();
		Map<String, Integer> taskReverseList = new TaskDAO(conn).individual_task_reverse(tname);
		Close(conn);
		return taskReverseList;
	}

	public Map<String, Integer> time_series_task(String memberName) {
		Connection conn = getConnection();
		Map<String, Integer> timeSeriesList = new TaskDAO(conn).time_series_task(memberName);
		Close(conn);
		return timeSeriesList;
	}

}

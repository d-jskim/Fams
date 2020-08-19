package com.fam.DAO;

public interface TaskSql {
	
	/*SELECT ALL*/
	String task_selectAll = "{call task_selectAll(?, ?)}";
	/*SELECT MY TO DO LIST*/
	String task_selectMyList = "{call task_selectMyList(?, ? ,?)}";
	/*INSERT*/
	String task_insert = "{call task_insert(?, ?, ?, ?, ?)}";
	/*DELETE*/
	String task_delete = "{call task_delete(?)}";
	/*UPDATE*/
	String task_update = "{call task_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	//"UPDATE task SET task = ?, category = ?.......... WHERE taskNo = ?"; 마지막 ?는 taskNo
	String task_updateLike = "{call task_updateLike(?, ?)}"; //WHERE taskNo = ?"; 마지막 ?는 taskNo	
	//INSERT all
	String task_insertAll = "{call task_insertAll(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
	
	
	//Bar Chart
		String member_list = "{call member_list(?, ?)}";
		String task_per_member = "{call task_per_member(?, ?)}";
		String task_status = "{call task_status(?)}";
		String individual_task = "{call individual_task(?, ?, ?)}";
		String select_all_task = "{call select_all_task(?, ?)}";
		String individual_task_reverse = "{call individual_task_reverse(?, ?)}";
		String time_series_task = "{call time_series_task(?,?)}";
}

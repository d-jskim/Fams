package com.fam.DAO;

public interface MemberSql {
	///������ ȸ�� ��� �߰�
	String member_insert = "BEGIN MEMBER_INSERT(?,?,?,?); END;";
	
	String member_login = "{ ? = call MEMBER_LOGIN(?,?) }";
	
	String member_selectVO = "{call member_selectVO(?, ?)}";
	
	String member_memberList = "{call member_memberList(?, ?)}";
}



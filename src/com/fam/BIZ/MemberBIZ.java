package com.fam.BIZ;

import java.sql.*;
import java.util.*;

import com.fam.DAO.*;
import com.fam.VO.Member;

import static common.JDBCTemplate.*;

public class MemberBIZ {

	public int getLogIn(Member member) {
		
		Connection conn = getConnection();

		int rs = new MemberDAO(conn).getLogIn(member);
		Close(conn);
		return rs;
	}

	public Member member_selectVO(String memberId) {
		
		Member member = null;
		Connection conn = getConnection();

		member = new MemberDAO(conn).member_selectVO(memberId);
		Close(conn);
		return member;
	}
	
	public ArrayList<String> member_memberList(int familyId){
		ArrayList<String> memberList = null;
		Connection conn = getConnection();
		memberList = new MemberDAO(conn).member_memberList(familyId);
		
		return memberList;
	}

}

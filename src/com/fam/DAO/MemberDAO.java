package com.fam.DAO;

import oracle.jdbc.*;
import static common.JDBCTemplate.*;
import java.sql.*;
import java.util.*;

import com.fam.VO.Member;



public class MemberDAO implements MemberSql{
	private Connection conn;
	public MemberDAO(Connection conn) {
		this.conn=conn;
	} 
	
	public int getInsert(Member member) {
		int res = 0;
		CallableStatement Pstmt = null;
		try {
			Pstmt = conn.prepareCall(member_insert); // 쿼리호출
			
			Pstmt.setString(1, member.getMemberId());
			Pstmt.setString(2, member.getPassword());
			Pstmt.setInt(3, member.getFamilyId());
			Pstmt.setString(4, member.getNickname());
			Pstmt.execute();
			Commit(conn);
			
		}catch (Exception e) {
			Rollback(conn);
		} finally {
			Close(Pstmt);
		}
		return res;
	}
	
	
	/**
	 * login
	 * @param member
	 * @return
	 */
	public int getLogIn(Member member) {

	int res = 0; //rs : resultSet  / res : result로 통일.
	
	CallableStatement cstmt =null;
	
	try {

		cstmt = conn.prepareCall(member_login);
        cstmt.setQueryTimeout(1800);
        cstmt.setString(2, member.getMemberId());
        cstmt.setString(3, member.getPassword());
        cstmt.registerOutParameter(1, java.sql.Types.INTEGER);
        cstmt.executeUpdate();
        res = cstmt.getInt(1);

        Commit(conn);
        
	}catch (Exception e) {
		Rollback(conn);
	} finally {
		Close(cstmt);
	}
	return res;
	}	
	
	public Member member_selectVO(String memberId) {

		CallableStatement cstmt = null;
		Member m = null;
		ResultSet rs = null;
		
		try {
			cstmt = conn.prepareCall(member_selectVO);
			cstmt.setString(1, memberId);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();
			Commit(conn);

			rs =((OracleCallableStatement)cstmt).getCursor(2);
			
			while (rs.next()) {
				m = new Member(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			Close(rs);
			Close(cstmt);
		}
		
		return m;
	}
	
	public ArrayList<String> member_memberList(int familyId) {
		
		ArrayList<String> memberList = new ArrayList<>();
		CallableStatement cstmt = null;
		ResultSet rs = null;
		
		try {
			cstmt = conn.prepareCall(member_memberList);
			cstmt.setInt(1, familyId);
			cstmt.registerOutParameter(2, OracleTypes.CURSOR);
			cstmt.execute();
			Commit(conn);

			rs =((OracleCallableStatement)cstmt).getCursor(2);
			
			while (rs.next()) {
				memberList.add(rs.getString(1));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			Close(rs);
			Close(cstmt);
		}
		
		return memberList;
	}
	
}//class end

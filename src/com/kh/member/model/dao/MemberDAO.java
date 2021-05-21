package com.kh.member.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.member.vo.Member;

public class MemberDAO {

	private Properties prop = null;
	
	public MemberDAO(){
		try {
		prop = new Properties();
		prop.load(new FileReader("query.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Member login(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member member = null;
		
		String query = prop.getProperty("login");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  m.getMemberId());
			pstmt.setString(2,  m.getMemberPwd());
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				member = new Member(rset.getString("member_Id"),
							 		rset.getString("member_pwd"),
							 		rset.getString("member_name"),
							 		rset.getString("gender").charAt(0),
							 		rset.getString("email"),
							 		rset.getString("phone"),
							 	    rset.getInt("age"),
							 	    rset.getString("address"),
							 	    rset.getDate("enroll_date"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
		//	close(pstmt);
		}
	
		return member;
		
	}
	
		
	}
	



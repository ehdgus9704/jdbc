package com.kh.member.model.service;
import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;

import com.kh.member.model.dao.MemberDAO;
import com.kh.member.vo.Member;

public class MemberService {

	private MemberDAO mDAO = new MemberDAO();
	
	public Member login(Member m) {
		Connection conn = getConnection();
		
		Member mem = mDAO.login(conn, m);
		
		return mem;
		
	}

	public void exitProgram() {
		Connection conn = getConnection();
		close(conn);
		
	}

}

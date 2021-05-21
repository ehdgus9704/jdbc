package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.getConnection;
import static com.kh.common.JDBCTemplate.commit;
import static com.kh.common.JDBCTemplate.rollback;
import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDAO;
import com.kh.board.model.vo.Board;


public class BoardService {

	
	private BoardDAO bDAO = new BoardDAO();
		
	public ArrayList<Board> selectAll() {
		Connection conn = getConnection();
		
		ArrayList<Board> bList = bDAO.selectAll(conn);
		
		
		return bList;
	}

	public int insertBoard(Board board) {
	 Connection conn = getConnection();
	 
	 int result = bDAO.insertBoard(conn, board);
	 
	 if(result > 0) {
		 commit(conn);
	 }else {
		 rollback(conn);
	 }
		return result;
	}

	public Board selectOne(int no) {
		Connection conn = getConnection();
		
		Board board =  bDAO.selectOne(conn,no);
		
		return board;
	}

	public int updateBoard(int no, String selStr, String upStr) {
		Connection conn = getConnection();
		
		int result = bDAO.updateBoard(conn, no, selStr, upStr);
		
		
		
		if(result > 0){
			commit(conn);
		}else {
			rollback(conn);
		}
		return result;
		
	}

	public int deleteBoard(int no) {
		Connection conn = getConnection();
		
		int result = bDAO.deleteBoard(conn, no);
		if(result > 0) {
			 commit(conn);
		 }else {
			 rollback(conn);
		 }
		return result;	
		}
		
	}

	
	
	



package com.kh.board.model.dao;

import static com.kh.common.JDBCTemplate.close;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Board;


public class BoardDAO {
	
	private Properties prop = null;
	
	public BoardDAO() {
		
		try {
			prop = new Properties();
			prop.load(new FileReader("query.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public ArrayList<Board> selectAll(Connection conn) {
		Statement stmt = null;
		ResultSet rset = null;
		ArrayList<Board> bList = null;
		
		String query = prop.getProperty("selectAll");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			bList = new ArrayList<Board>();
			while(rset.next()) {
				Board board = new Board(rset.getInt("bno"),
										rset.getString("title"),
										rset.getString("content"),
										rset.getDate("create_date"),
										rset.getString("writer"));
				
				bList.add(board);
										
						
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			//close(stmt);
		}
		
		
		return bList;
	}
	public int insertBoard(Connection conn, Board board) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  board.getTitle());
			pstmt.setString(2,  board.getContent());
			pstmt.setString(3,  board.getWriter());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//close(pstmt);
		}
		
		return result;
	}
	public Board selectOne(Connection conn, int no) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Board  board = null;
		String query = prop.getProperty("selectOne");
		
		try {
			pstmt =  conn.prepareStatement(query);
			pstmt.setInt(1, no);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				 board = new Board(rset.getInt("bno"),
								  rset.getString("title"),
								  rset.getString("content"),
								  rset.getDate("create_date"),
								  rset.getString("writer"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
		//	close(pstmt);
		}
		return board;
	}
	public int updateBoard(Connection conn, int no, String selStr, String upStr) {
		PreparedStatement pstmt = null;
		int result = 0;
		// UPDATE BOARD SET ?=? WHERE BNO = ? = 위치홀더에는 자동으로 싱글쿼테이션이 들어가서 리터럴로 인식되기 때문에
		
		String query = prop.getProperty("update " + selStr);
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,  upStr);
			pstmt.setInt(2,  no);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
		//close(pstmt);
		}
		
		
		return result;
	}
	public int deleteBoard(Connection conn, int no) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = prop.getProperty("deleteBoard");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//close(pstmt);
		}
		return result;
	}
	
	

}

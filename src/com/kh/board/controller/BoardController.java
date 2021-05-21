package com.kh.board.controller;

import java.util.ArrayList;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.view.View;

public class BoardController {

	private BoardService bService = new BoardService();
	private View view = new View();
	
	public void selectAll() {
		//게시글 전체 데이터 가져오기
		ArrayList<Board> bList = bService.selectAll();
		
		
		//게시글 전체 데이터를 가져오는데 성공하면 view의 selectAll로 넘어가  리스트 출력하고
		// 실패하면 view 의 displayError로 넘어가 "조회 결과가 없습니다." 출력
		if(!bList.isEmpty()) {
			view.selectAll(bList);
		}else {
			view.displayError("조회결과가 없습니다.");
		}
			
		
	}

	public void insertBoard() {
		Board board = view.insertBoard();
		
		int result = bService.insertBoard(board);
		
		if(result > 0) {
			view.displaySuccess("게시글이 등록되었습니다.");
		}else {
			view.displayError("게시글 등록 과정 중 오류 발생");
		}
		
	}

	public void selectOne() {
		int no = view.inputBNo();
		
		Board board = bService.selectOne(no);
		
		if(board != null){
			view.selectOne(board);
		}else{
			view.displayError("해당 글이 존재하지 않습니다.");
		}
		
	}

	public void updateBoard() {
		int no = view.inputBNo();
		
		Board board = bService.selectOne(no);
		
		if(board != null) {
			String memberId = view.getMemberId();
			
			if(board.getWriter().equals(memberId)) {
				
				int sel = view.updateMenu();
				
				String selStr = null; //title, content 종류
				String upStr = null;// 수정할 내용
				switch(sel) {
				case 1:
					selStr = "Title";
					upStr = view.updateTitle();
					break;
				case 2:
					selStr = "Content";
					upStr = view.updateContent();
					break;
				case 0: return;
				}
				
				int result = bService.updateBoard(no, selStr, upStr);
				
				if(result > 0) {
					view.displaySuccess("게시글이 수정되었습니다.");
				}else {
					view.displayError("게시글 수정 과정 중 오류 발생");
				}
			}else {
				view.displayError("해당 글을 수정할 수 없습니다,");
			}
			
		}else {
			view.displayError("해당 번호의 글은 존재하지 않습니다.");
		}
		
	}

	public void deleteBoard() {
		int no = view.inputBNo();
		
		Board board = bService.selectOne(no);
		
		if(board != null) {
			
			String memberId = view.getMemberId();
			
			if(board.getWriter().equals(memberId)) {
				
				char yn = view.deleteBoard();
				
				if(yn == 'N')return;
				else if(yn == 'Y') {
				  int result = bService.deleteBoard(no);	
				  
				  if(result > 0) {
					  view.displaySuccess("게시글이 삭제되었습니다.");
				  }else {
					  view.displayError("게시글 삭제 과정 중 오류 발생");
				  }
				}
				
				
			}else {
				view.displayError("해당 글을 삭제할 수 없습니다.");
			}
			
		}else {
			view.displayError("해당 번호의 글이 존재 하지 않습니다.");
		}
		
	}

}
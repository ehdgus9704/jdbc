package com.kh.member.controller;

import com.kh.member.model.service.MemberService;
import com.kh.member.vo.Member;
import com.kh.view.View;

public class MemberController {
	
	private MemberService mService = new MemberService();
	private View view = new View(); 

	public void login() {
	 Member m =  view.inputLogin();
	Member member = mService.login(m);
	
	if(member != null) {
		view.displayLoginSuccess(member);
	}else {
		view.displayError("로그인 정보를 확인해주세요.");
	}
	
		
	}

	public void exitProgram() {
		mService.exitProgram();
		
	}

}

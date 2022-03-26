package com.miracle.view;

public class ResultView {

	public void Success() {
		System.out.println("======================== 노트 등록 성공!! ========================");
	}
	
	public void failed() {
		System.out.println("======================== 노트 등록 실패!! ========================");
	}
	
	public void updateSuccess() {
		System.out.println("======================== 노트 수정 성공!! ========================");
	}
	
	public void updatefailed() {
		System.out.println("======================== 노트 수정 실패!! ========================");
	}
	
	public void deleteSuccess() {
		System.out.println("======================== 노트 삭제 성공!! ========================");
	}
	
	public void deletefailed() {
		System.out.println("======================== 노트 삭제 실패!! ========================");
	}
	
}

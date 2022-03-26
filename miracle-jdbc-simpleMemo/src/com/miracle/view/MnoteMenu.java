package com.miracle.view;

import java.util.Scanner;


import com.miracle.controller.MnoteController;

public class MnoteMenu {

	
	public void displayMainMenu() {
		
	
	Scanner sc = new Scanner(System.in);
	MnoteController noteCon = new MnoteController();
	
	menu:
	while(true) {
		System.out.println("======= My Note =======");
		System.out.println(" 1. 노트 작성");
		System.out.println(" 2. 노트 목록 조회");
		System.out.println(" 3. 노트 열어서 수정하기");
		System.out.println(" 4. 노트 삭제하기");
		System.out.println(" 5. 끝내기");
		System.out.print("번호를 입력하세요 . : ");
		int sel = sc.nextInt();
		
		switch(sel) {
		case 1 : 
			noteCon.newNote();
			break;
		case 2 :
			noteCon.selNote();
			break;
		case 3 :
			noteCon.updateNote();
			break;
		case 4 :
			noteCon.delNote();
			break;
		case 5 : 
			System.out.println("프로그램을 종료합니다.");
			break menu;
		}
		
	}
	
		
	}
	
	
}



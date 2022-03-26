package com.miracle.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.miracle.model.dto.MnoteDTO;
import com.miracle.model.dto.RecordDTO;
import com.miracle.model.service.MnoteService;
import com.miracle.view.ResultView;

public class MnoteController {


	MnoteService mnoteService = new MnoteService();
	Scanner sc = new Scanner(System.in);
	ResultView resultView = new ResultView();

	
	public void newNote() {
		
		MnoteDTO mnoteDTO = new MnoteDTO();
		RecordDTO recordDTO = new RecordDTO();
		
		
		System.out.print("작성자를 입력해주세요 : ");
		String author = sc.nextLine();
		
		System.out.print("노트 제목을 입력해주세요 : ");
		String title = sc.nextLine();
		
		System.out.print("노트 내용을 입력해주세요 : ");
		String content = sc.nextLine();
	 	
		java.util.Date mnoteTime = new java.util.Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
		String date = dateFormat.format(mnoteTime);
	
		mnoteDTO.setTitle(title);
		mnoteDTO.setContent(content);
		mnoteDTO.setDate(date);
		
	
		recordDTO.setAuthor(author);
		recordDTO.setNoteDate(date);
		recordDTO.setState("I");
		
		int result1 = mnoteService.newNote(mnoteDTO);
		int result2 = mnoteService.newNoteRecord(recordDTO);
		
		if(result1 > 0 && result2 > 0) {
			resultView.Success();
		} else {
			resultView.failed();
		}
	}

	public void selNote() {
		
		List<MnoteDTO> mnoteDTO = new ArrayList<>();
		
		mnoteDTO = mnoteService.selNote();
		
		for(MnoteDTO mnoteDTO1 : mnoteDTO) {
			System.out.println(mnoteDTO1);
		}
		
	}

	public void updateNote() {
		
		MnoteDTO mnoteDTO = new MnoteDTO();
		RecordDTO recordDTO = new RecordDTO();
		
		System.out.print("수정할 노트 번호를 입력해주세요 : ");
		int selNo = sc.nextInt();
		
		mnoteService.selNoteNo(selNo);
		mnoteDTO.setMemoNo(selNo);
		recordDTO.setNo(selNo);
		
		sc.nextLine();
		
		System.out.print("수정할 제목을 입력해주세요 : ");
		String title = sc.nextLine();
		
		System.out.print("수정할 내용을 입력해주세요 : ");
		String content = sc.nextLine();
		
		
		mnoteDTO.setTitle(title);
		mnoteDTO.setContent(content);
		
		
		java.util.Date mnoteTime = new java.util.Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
		String date = dateFormat.format(mnoteTime);
		
		recordDTO.setState("U");
		recordDTO.setNoteDate(date);
		
		
		
		int result1 = mnoteService.updateNote(mnoteDTO);
		int result2 = mnoteService.updateRecord(recordDTO);
		
		
		if(result1 > 0 && result2 > 0) {
			resultView.updateSuccess();
		} else {
			resultView.updatefailed();
		}
	}

	public void delNote() {
		
		RecordDTO recordDTO = new RecordDTO();
		
		System.out.print("삭제할 노트 번호를 입력해주세요 : ");
		int selNo = sc.nextInt();
		
		java.util.Date mnoteTime = new java.util.Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy/MM/dd");
		String date = dateFormat.format(mnoteTime);
		
	    recordDTO.setState("D");
	    recordDTO.setNoteDate(date);

		int result1 = mnoteService.delNote(selNo);
		int result2 = mnoteService.delRecord(selNo, recordDTO);
		
		if(result1 > 0 && result2 > 0) {
			resultView.deleteSuccess();
		} else {
			resultView.deletefailed();
		}
		
	}

	
	
	
	
}

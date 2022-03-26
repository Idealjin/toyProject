package com.miracle.model.dto;

import java.util.Date;

public class MnoteDTO {

	private int memoNo;
	private String title;
	private String content;
	private String date;
	private boolean delYn;
	
	public MnoteDTO() {
		super();
		
	}

	public MnoteDTO(int memoNo, String title, String content, String date, boolean delYn) {
		super();
		this.memoNo = memoNo;
		this.title = title;
		this.content = content;
		this.date = date;
		this.delYn = delYn;
	}

	public int getMemoNo() {
		return memoNo;
	}

	public void setMemoNo(int memoNo) {
		this.memoNo = memoNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public boolean isDelYn() {
		return delYn;
	}

	public void setDelYn(boolean delYn) {
		this.delYn = delYn;
	}

	@Override
	public String toString() {
		return "MnoteDTO [memoNo=" + memoNo + ", title=" + title + ", content=" + content + ", date=" + date
				+ ", delYn=" + delYn + "]";
	}
	
	
	
	
	
	
}

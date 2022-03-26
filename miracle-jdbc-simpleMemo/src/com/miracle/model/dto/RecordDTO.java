package com.miracle.model.dto;

public class RecordDTO {

	private int no;
	private String author;
	private String noteDate;
	private String state;
	
	public RecordDTO() {
		super();
	}

	public RecordDTO(int no, String author, String noteDate, String state) {
		super();
		this.no = no;
		this.author = author;
		this.noteDate = noteDate;
		this.state = state;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getNoteDate() {
		return noteDate;
	}

	public void setNoteDate(String noteDate) {
		this.noteDate = noteDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "RecordDTO [no=" + no + ", author=" + author + ", noteDate=" + noteDate + ", state=" + state + "]";
	}
	
	
	
	
	
}

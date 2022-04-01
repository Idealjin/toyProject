package com.ohgiraffers.spring.board.model.dto;

public class CategoryDTO {

	private int code;
	private String name;
	
	public CategoryDTO() {
		super();
	
	}

	public CategoryDTO(int code, String name) {
		super();
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "CategoryDTO [code=" + code + ", name=" + name + "]";
	}
	
	
	
	
}

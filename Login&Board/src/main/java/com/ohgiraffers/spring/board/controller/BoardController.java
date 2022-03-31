package com.ohgiraffers.spring.board.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ohgiraffers.spring.board.model.service.BoardService;
import com.ohgiraffers.spring.common.paging.SelectCriteria;

@Controller
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	
	@Autowired
	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}
	
	@GetMapping("/list")
	public ModelAndView BoardSelectList(@RequestParam(defaultValue = "1") int currentPage,
				@ModelAttribute SelectCriteria searchCriteria, ModelAndView mv) {
		
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("searchCondition", searchCriteria.getSearchCondition());
		searchMap.put("searchValue", searchCriteria.getSearchValue());
		System.out.println("컨트롤러에서 검색 조건 확인하기 : " + searchMap);
		
		//전체 게시물 수가 필요하다.
		// 1. 데이터베이스에서 먼저 전체 게시물 수를 조회
		// 2. 검색 조건이 있는 경우 검색 조건에 맞는 전체 게시물 수를 조회
		
		int totalCount = boardService.selectTotalCount(searchMap);
		
		System.out.println("totalBoardCount : " + totalCount);

		return mv;
		
	}
	
}

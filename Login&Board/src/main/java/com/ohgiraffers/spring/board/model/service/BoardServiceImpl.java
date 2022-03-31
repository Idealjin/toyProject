package com.ohgiraffers.spring.board.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.ohgiraffers.spring.board.model.dao.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {

	private final BoardMapper mapper;
	
	public BoardServiceImpl(BoardMapper mapper) {
		this.mapper = mapper;
		
	}
	
	/**
	 *<pre>
	 * 해당 게시글 전체 갯수 조회용 메소드
	 *</pre>
	 */
	@Override
	public int selectTotalCount(Map<String, String> searchMap) {
		
		
		
		return mapper.selectTotalCount(searchMap);
	}
	

	
	
}

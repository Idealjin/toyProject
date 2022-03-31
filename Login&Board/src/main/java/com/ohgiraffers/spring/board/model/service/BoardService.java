package com.ohgiraffers.spring.board.model.service;

import java.util.Map;

public interface BoardService {

	int selectTotalCount(Map<String, String> searchMap);

}

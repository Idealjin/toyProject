package com.ohgiraffers.spring.board.model.dao;

import java.util.Map;

public interface BoardMapper {

	int selectTotalCount(Map<String, String> searchMap);

}

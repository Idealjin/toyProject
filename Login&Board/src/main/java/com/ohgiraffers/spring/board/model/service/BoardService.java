package com.ohgiraffers.spring.board.model.service;

import java.util.List;
import java.util.Map;

import com.ohgiraffers.spring.board.model.dto.BoardDTO;
import com.ohgiraffers.spring.board.model.dto.ReplyDTO;
import com.ohgiraffers.spring.common.exception.board.ReplyRegistException;
import com.ohgiraffers.spring.common.exception.board.ReplyRemoveException;
import com.ohgiraffers.spring.common.exception.board.ThumbnailRegistException;
import com.ohgiraffers.spring.common.paging.SelectCriteria;

public interface BoardService {

	int selectTotalCount(Map<String, String> searchMap);
	
	List<BoardDTO> selectBoardList(SelectCriteria selectCriteria);

	BoardDTO selectBoardDetail(int no);

	List<ReplyDTO> registReply(ReplyDTO registReply) throws ReplyRegistException;

	List<ReplyDTO> selectAllReplyList(int no);

	List<ReplyDTO> removeReply(ReplyDTO removeReply) throws ReplyRemoveException;

	List<BoardDTO> selectAllThumbnailList();
	
	void registThumbnail(BoardDTO thumbnail) throws ThumbnailRegistException;

	BoardDTO selectThumbnailDetail(int no);

	
}

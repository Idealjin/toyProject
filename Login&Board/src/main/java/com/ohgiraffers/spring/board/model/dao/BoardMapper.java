package com.ohgiraffers.spring.board.model.dao;

import java.util.List;
import java.util.Map;

import com.ohgiraffers.spring.board.model.dto.AttachmentDTO;
import com.ohgiraffers.spring.board.model.dto.BoardDTO;
import com.ohgiraffers.spring.board.model.dto.ReplyDTO;
import com.ohgiraffers.spring.common.paging.SelectCriteria;

public interface BoardMapper {

	int selectTotalCount(Map<String, String> searchMap);

	List<BoardDTO> selectBoardList(SelectCriteria selectCriteria);

	BoardDTO selectBoardDetail(int no);

	int insertReply(ReplyDTO registReply);

	List<ReplyDTO> selectReplyList(int refBoardNo);

	int deleteReply(int no);

	int insertThumbnailContent(BoardDTO thumbnail);

	int insertAttachment(AttachmentDTO attachmentDTO);

	List<BoardDTO> selectAllThumbnailList();

	int incrementBoardCount(int no);

	BoardDTO selectThumbnailDetail(int no);

	
}

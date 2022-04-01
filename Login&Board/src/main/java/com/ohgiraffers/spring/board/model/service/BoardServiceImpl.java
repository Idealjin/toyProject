package com.ohgiraffers.spring.board.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ohgiraffers.spring.board.model.dao.BoardMapper;
import com.ohgiraffers.spring.board.model.dto.AttachmentDTO;
import com.ohgiraffers.spring.board.model.dto.BoardDTO;
import com.ohgiraffers.spring.board.model.dto.ReplyDTO;
import com.ohgiraffers.spring.common.exception.board.ReplyRegistException;
import com.ohgiraffers.spring.common.exception.board.ReplyRemoveException;
import com.ohgiraffers.spring.common.exception.board.ThumbnailRegistException;
import com.ohgiraffers.spring.common.paging.SelectCriteria;

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
	

	/**
	 * <pre>
	 *   게시글 전체 조회용 메소드
	 * </pre>
	 * @param selectCriteria 조회용 정보
	 */
	@Override
	public List<BoardDTO> selectBoardList(SelectCriteria selectCriteria) {
		
		return mapper.selectBoardList(selectCriteria);
	}

	/**
	 *<pre>
	 *	게시글 상세 페이지 조회용 메소드
	 *</pre>
	 * @param no 게시글 번호
	 */
	@Override
	public BoardDTO selectBoardDetail(int no) {
		
		
		return mapper.selectBoardDetail(no);
	}

	/**
	 * <pre>
	 * 	댓글 등록용 메소드
	 * </pre>
	 * @param registReply 댓글 등록 정보
	 * @throws ReplyRegistException 
	 */
	@Override
	public List<ReplyDTO> registReply(ReplyDTO registReply) throws ReplyRegistException {
		
		List<ReplyDTO> replyList = null;
		
		//댓글 등록
		int result = mapper.insertReply(registReply);
		
		//댓글 등록이 성공하면 댓글 리스트를 조회
		if(result > 0) {
			replyList = mapper.selectReplyList(registReply.getRefBoardNo());
		} else {
			throw new ReplyRegistException("댓글 등록에 실패하셨습니다.");
		}
		return replyList;
	}

	/**
	 * <pre>
	 *  해당 게시글의 전체 댓글 조회용 메소드
	 * </pre>
	 * @param no 게시글 번호
	 */
	@Override
	public List<ReplyDTO> selectAllReplyList(int no) {

		List<ReplyDTO> replyList = null;
		
		replyList = mapper.selectReplyList(no);
		
		return replyList;
	}

	/**
	 * <pre>
	 *   댓글 삭제용 메소드
	 * </pre>
	 * @throws ReplyRemoveException 
	 */
	@Override
	public List<ReplyDTO> removeReply(ReplyDTO removeReply) throws ReplyRemoveException {
	
		List<ReplyDTO> replyList = null;
		System.out.println(removeReply);
		
		int result = mapper.deleteReply(removeReply.getNo());
		
		
		if(result > 0) {
			replyList = mapper.selectReplyList(removeReply.getRefBoardNo());
		} else {
			throw new ReplyRemoveException("댓글 삭제에 실패하셨습니다.");			
		}
		
		return replyList;
	}

	/**
	 * <pre>
	 *   전체 썸네일 게시글 조회용 메소드
	 * </pre>
	 */
	@Override
	public List<BoardDTO> selectAllThumbnailList() {
		
		return mapper.selectAllThumbnailList();
	}

	/**
	 * <pre>
	 *   썸네일 게시글 등록용 메소드
	 * </pre>
	 * @throws ThumbnailRegistException 
	 */
	@Override
	public void registThumbnail(BoardDTO thumbnail) throws ThumbnailRegistException {
		
		int result = 0;
		
		/* 먼저 board 테이블부터 insert 한다. */
		int boardResult = mapper.insertThumbnailContent(thumbnail);
		
		/* Attachment 리스트를 불러온다. */
		List<AttachmentDTO> attachmentList = thumbnail.getAttachmentList();
		
		/* fileList에 boardNo값을 넣는다. */
		for(int i = 0; i < attachmentList.size(); i++) {
			attachmentList.get(i).setRefBoardNo(thumbnail.getNo());
		}
		
		/* Attachment 테이블에 list size만큼 insert 한다. */
		int attachmentResult = 0;
		for(int i = 0; i < attachmentList.size(); i++) {
			attachmentResult += mapper.insertAttachment(attachmentList.get(i));
		}
		
		/* 게시글 추가 및 첨부파일 갯수 만큼 첨부파일 내용 insert에 실패 시 예외 발생 */ 
		if(!(boardResult > 0 && attachmentResult == attachmentList.size())) {
			throw new ThumbnailRegistException("사진 게시판 등록에 실패하셨습니다.");
		}
	}

	/**
	 *  <pre>
	 *    게시글 상세 페이지 조회용 메소드
	 *  </pre>
	 *  @param no 게시글 번호
	 */
	@Override
	public BoardDTO selectThumbnailDetail(int no) {
	
		BoardDTO thumbnailDetail = null;
		
		int result = mapper.incrementBoardCount(no);
		
		if(result > 0) {
			thumbnailDetail = mapper.selectThumbnailDetail(no);
		}
		
		return thumbnailDetail;
	}


	
	
}

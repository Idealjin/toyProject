package com.ohgiraffers.spring.notice.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ohgiraffers.spring.common.exception.notice.NoticeModifyException;
import com.ohgiraffers.spring.common.exception.notice.NoticeRegistException;
import com.ohgiraffers.spring.common.exception.notice.NoticeRemoveException;
import com.ohgiraffers.spring.notice.model.dao.NoticeMapper;
import com.ohgiraffers.spring.notice.model.dto.NoticeDTO;

@Service
public class NoticeServiceImpl implements NoticeService {

	private final NoticeMapper mapper;
	
	@Autowired
	public NoticeServiceImpl(NoticeMapper mapper) {
		
		this.mapper = mapper;
	}
	
	/**
	 *	<pre>
	 *    공지사항 전체 목록 조회용 메소드
	 *  </pre>
	 */
	@Override
	public List<NoticeDTO> selectAllNoticeList() {
		
		List<NoticeDTO> noticeList = mapper.selectAllNoticeList();
		
		return noticeList;
	}

	/**
	 * <pre>
	 * 	공지사항 등록용 메소드
	 * </pre>
	 * @param notice 공지사항 등록 정보
	 * @throws NoticeRegistException 
	 */
	@Override
	public void registNotice(NoticeDTO notice) throws NoticeRegistException {
		
		int result = mapper.insertNotice(notice);
		
		if(!(result > 0)) {
			
			throw new NoticeRegistException("공지사항 등록에 실패하셨습니다.");
		}
	}

	/**
	 * <pre>
	 * 	 공지사항 상세 페이지 조회용 메소드
	 * </pre>
	 * @param no 공지사항 번호
	 */
	@Override
	public NoticeDTO selectNoticeDetail(int no) {
	
		NoticeDTO noticeDetail = null;
		
		/* 1. 조회수를 증가 */
		int result = mapper.incrementNoticeCount(no);
		
		/* 2. 조회수 증가 성공 후 상세보기 */
		if(result > 0) {
			noticeDetail = mapper.selectNoticeDetail(no);
		}
		
		return noticeDetail;
	}

	/**
	 * <pre>
	 *   공지사항 수정용 메소드
	 * </pre>
	 * @param notice 수정 데이터
	 * @throws NoticeModifyException 
	 */
	@Override
	public void modifyNotice(NoticeDTO notice) throws NoticeModifyException {
		
		int result = mapper.updateNotice(notice);
		
		if(!(result > 0)) {
			
			throw new NoticeModifyException("공지사항 수정에 실패하셨습니다.");
		}
	}

	/**
	 * <pre>
	 * 	공지사항 삭제용 메소드
	 * </pre>
	 * @param no 공지사항 삭제 번호
	 * @throws NoticeRemoveException 
	 */
	@Override
	public void removeNotice(int no) throws NoticeRemoveException {
	
		int result = mapper.deleteNotice(no);
		
		if(!(result > 0)) {
			
			throw new NoticeRemoveException("공지사항 삭제에 실패하셨습니다.");
		}
	}

}

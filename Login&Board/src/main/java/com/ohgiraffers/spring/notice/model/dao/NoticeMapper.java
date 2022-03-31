package com.ohgiraffers.spring.notice.model.dao;

import java.util.List;

import com.ohgiraffers.spring.notice.model.dto.NoticeDTO;

public interface NoticeMapper {


	List<NoticeDTO> selectAllNoticeList();

	int insertNotice(NoticeDTO notice);

	int incrementNoticeCount(int no);

	NoticeDTO selectNoticeDetail(int no);

	int updateNotice(NoticeDTO notice);

	int deleteNotice(int no);


}

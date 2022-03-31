package com.ohgiraffers.spring.notice.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ohgiraffers.spring.common.exception.notice.NoticeModifyException;
import com.ohgiraffers.spring.common.exception.notice.NoticeRegistException;
import com.ohgiraffers.spring.common.exception.notice.NoticeRemoveException;
import com.ohgiraffers.spring.member.model.dto.MemberDTO;
import com.ohgiraffers.spring.notice.model.dto.NoticeDTO;
import com.ohgiraffers.spring.notice.model.service.NoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {

	private final NoticeService noticeService;
	
	@Autowired
	public NoticeController(NoticeService noticeService) {
		this.noticeService = noticeService;
	}
	
	@GetMapping("/list")
	public String selectAllNoticeList(Model model) {
		
		List<NoticeDTO> noticeList = noticeService.selectAllNoticeList();
		
		model.addAttribute("noticeList", noticeList);
		
		return "/notice/noticeList";
	}
	
	@GetMapping("/regist")
	public void registNotice() {}
	
	@PostMapping("/regist")
	public String registNotice(@ModelAttribute NoticeDTO notice, HttpServletRequest request, 
	        RedirectAttributes rttr) throws NoticeRegistException {
		
		int writerMemberNo = ((MemberDTO) request.getSession().getAttribute("loginMember")).getNo();
		
		notice.setWriterMemberNo(writerMemberNo);
		
		noticeService.registNotice(notice);
		
		rttr.addFlashAttribute("message", "공지사항 등록에 성공하셨습니다.");
		
		return "redirect:/notice/list";
		
	}
	
	
	@GetMapping("/detail")
	public String selectNoticeDetail(@RequestParam int no, Model model) {
		
		System.out.println("no : " + no);
		
		NoticeDTO noticeDetail = noticeService.selectNoticeDetail(no);
		
		model.addAttribute("notice", noticeDetail);
		
		return "/notice/noticeDetail";
	}
	
	@GetMapping("/update")
	public String modifyNotice(@RequestParam int no, Model model) {
		
		NoticeDTO notice = noticeService.selectNoticeDetail(no);
		
		model.addAttribute("notice", notice);
		
		return "/notice/update";
	}
	
	@PostMapping("/update")
	public String modifyNotice(@ModelAttribute NoticeDTO notice, RedirectAttributes rttr) throws NoticeModifyException {
		
		System.out.println("notice : " + notice);
		
		noticeService.modifyNotice(notice);
		
		rttr.addFlashAttribute("message", "공지사항 수정에 성공하셨습니다!");
		
		return "redirect:/notice/list";
	}

	@GetMapping("/delete")
	public String removeNotice(@RequestParam int no, RedirectAttributes rttr) throws NoticeRemoveException {
		
		noticeService.removeNotice(no);
		
		rttr.addFlashAttribute("message", "공지사항 삭제에 성공하였습니다.");
		
		return "redirect:/notice/list";
	}

	
	
	
	
	
}

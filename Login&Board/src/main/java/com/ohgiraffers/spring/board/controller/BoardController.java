package com.ohgiraffers.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

import com.ohgiraffers.spring.board.model.dto.BoardDTO;
import com.ohgiraffers.spring.board.model.dto.ReplyDTO;
import com.ohgiraffers.spring.board.model.service.BoardService;
import com.ohgiraffers.spring.common.exception.board.ReplyRegistException;
import com.ohgiraffers.spring.common.exception.board.ReplyRemoveException;
import com.ohgiraffers.spring.common.paging.Pagenation;
import com.ohgiraffers.spring.common.paging.SelectCriteria;
import com.ohgiraffers.spring.member.model.dto.MemberDTO;

@Controller
@RequestMapping("/board")
public class BoardController {

	private final BoardService boardService;
	
	@Autowired
	public BoardController(BoardService boardService) {
		
		this.boardService = boardService;
	}
	
//	public ModelAndView BoardSelectList(HttpServletRequest request, @ModelAttribute SelectCriteria searchCriteria,
//	         ModelAndView mv) {
//		
//		String currentPage = request.getParameter("currentPage");   "2"
//		int pageNo = 1;
//		
//		if(currentPage != null && !"".equals(currentPage)) {
//			pageNo = Integer.parseInt(currentPage);
//		}
//		
//	}
	
	@GetMapping("/list")
	public ModelAndView BoardSelectList(@RequestParam(defaultValue = "1") int currentPage, @ModelAttribute SelectCriteria searchCriteria,
	         ModelAndView mv) {
	
		String searchCondition = searchCriteria.getSearchCondition();
		String searchValue = searchCriteria.getSearchValue();
		
		Map<String, String> searchMap = new HashMap<>();
		searchMap.put("searchCondition", searchCondition);
		searchMap.put("searchValue", searchValue);
		
		System.out.println("?????????????????? ?????? ?????? ???????????? : " + searchMap);
		
		/* ?????? ????????? ?????? ????????????. 
		 * 1. ???????????????????????? ?????? ?????? ????????? ?????? ??????
		 * 2. ??????????????? ?????? ?????? ?????? ????????? ?????? ?????? ????????? ?????? ??????
		 * */
		int totalCount = boardService.selectTotalCount(searchMap);
		
		System.out.println("totalBoardCount : " + totalCount);
		
		/* ??? ???????????? ????????? ????????? ??? */
		int limit = 10;
		
		/* ??? ?????? ????????? ????????? ????????? ??????*/
		int buttonAmount = 5; 
		
		/* ????????? ????????? ?????? ?????? ?????? ??? ????????? ????????? ?????? ????????? ?????? ?????? ??????????????? ??????*/
		SelectCriteria selectCriteria = null;
		
		if(searchCondition != null && !"".equals(searchCondition)) {
		
			selectCriteria = Pagenation.getSelectCriteria(currentPage, totalCount, limit, buttonAmount, searchCondition, searchValue);
		} else {
			selectCriteria = Pagenation.getSelectCriteria(currentPage, totalCount, limit, buttonAmount);
		}
		
		System.out.println("selectCriteria : " + selectCriteria);
		
		List<BoardDTO> boardList = boardService.selectBoardList(selectCriteria);
		
		System.out.println("boradList : " + boardList);
		
		mv.addObject("boardList", boardList);
		mv.addObject("selectCriteria", selectCriteria);
		mv.setViewName("/board/boardList");
		
		return mv;
	}
	@GetMapping("/detail")
	public String selectBoardDetail(@RequestParam int no, Model model) {
		BoardDTO boardDetail = boardService.selectBoardDetail(no);
		
		model.addAttribute("board", boardDetail);
		
		//?????? ??????
		List<ReplyDTO> replyList = boardService.selectAllReplyList(no);
		model.addAttribute("replyList", replyList);
		
		
		
		return "/board/boardDetail";
	}
	
	@PostMapping(value="/registReply", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<ReplyDTO> registReply(@ModelAttribute ReplyDTO registReply, HttpServletRequest request) throws ReplyRegistException{
		
		registReply.setWriterMemberNo(((MemberDTO) request.getSession().getAttribute("loginMember")).getNo());
		
		List<ReplyDTO> replyList = boardService.registReply(registReply);
		
		return replyList;
	}
	
	@PostMapping(value="/removeReply", produces="application/json; charset=UTF-8")		
	@ResponseBody
	public List<ReplyDTO> removeReply(@ModelAttribute ReplyDTO removeReply) throws ReplyRemoveException {
		
		List<ReplyDTO> replyList = boardService.removeReply(removeReply);
		
		return replyList;
	}

	
	
	
	
}

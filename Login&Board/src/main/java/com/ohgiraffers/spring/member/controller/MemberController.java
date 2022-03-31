package com.ohgiraffers.spring.member.controller;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ohgiraffers.spring.common.exception.member.LoginFailedException;
import com.ohgiraffers.spring.common.exception.member.MemberModifyException;
import com.ohgiraffers.spring.common.exception.member.MemberRegistException;
import com.ohgiraffers.spring.common.exception.member.MemberRemoveException;
import com.ohgiraffers.spring.member.model.dto.MemberDTO;
import com.ohgiraffers.spring.member.model.service.MemberService;

@Controller
@RequestMapping("/member")
@SessionAttributes("loginMember")
public class MemberController {

	private final MemberService memberService;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public MemberController(MemberService memberService, BCryptPasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/regist")
	public void registMember() {}
	
	@PostMapping(value="/idDupCheck", produces="text/plain; charset=utf-8")
	@ResponseBody
	public String checkDuplication(@RequestParam(required = false) String userId) {
		
		System.out.println("넘어온 값 출력 : " + userId);
		
		String result = "사용 가능한 아이디 입니다.";
		
		if("".equals(userId)) {
			result = "아이디를 입력해 주세요";
		} else if(memberService.selectMemberById(userId)) {
			result = "중복된 아이디가 존재합니다.";
		}
		
		return result;
	}
	
	@PostMapping("/regist")
	public String registMember(@ModelAttribute MemberDTO member, HttpServletRequest request, RedirectAttributes rttr) throws MemberRegistException {
		
		/* 사용자가 '-'기호를 사용하여 전화번호를 입력하는 경우 통일시키기 위해서 replace를 이용함 */
		String phone = request.getParameter("phone").replace("-", "");
		
		String address = request.getParameter("zipCode")+ "$" + request.getParameter("address1") + "$" + request.getParameter("address2");
		
		member.setAddress(address);
		member.setPwd(passwordEncoder.encode(member.getPwd()));
		System.out.println(member);
		
		memberService.registMember(member);
		
		rttr.addFlashAttribute("message","회원 가입에 성공하였습니다.");
		
		return "redirect:/";  
	}
	
	@PostMapping("/login")
	public String login(@ModelAttribute MemberDTO member, Model model) throws LoginFailedException {

		model.addAttribute("loginMember", memberService.findMember(member));
		
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(SessionStatus status) {
		
		status.setComplete();
		
		return "redirect:/";
	}
	
	@GetMapping("/update")
	public void modifyMember() {}
	
	@PostMapping("/update")
	public String modifyMember(@ModelAttribute MemberDTO member, HttpServletRequest request, RedirectAttributes rttr) throws MemberModifyException {
		
		String phone = request.getParameter("phone").replace("-", "");
		
		String address = request.getParameter("zipCode") + "$" + request.getParameter("address1") + "$" + request.getParameter("address2");
		
		member.setId(((MemberDTO) request.getSession().getAttribute("loginMember")).getId());
		member.setPhone(phone);
		member.setAddress(address);
		member.setPwd(passwordEncoder.encode(member.getPwd()));
		
		System.out.println("member : " + member);
		
		memberService.modifyMember(member);
		
		request.getSession().setAttribute("loginMember", member);
		
		return "redirect:/";
	}
	
	@GetMapping("/delete")
	public String deleteMember(@ModelAttribute MemberDTO member, SessionStatus status, RedirectAttributes rttr) throws MemberRemoveException {
		
		memberService.removeMember(member);
		
		rttr.addFlashAttribute("message", "회원 탈퇴에 성공하였습니다.");
		status.setComplete();
		
		return "redirect:/";
	}
	
	
}


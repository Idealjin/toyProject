package com.ohgiraffers.spring.member.model.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ohgiraffers.spring.common.exception.member.LoginFailedException;
import com.ohgiraffers.spring.common.exception.member.MemberModifyException;
import com.ohgiraffers.spring.common.exception.member.MemberRegistException;
import com.ohgiraffers.spring.common.exception.member.MemberRemoveException;
import com.ohgiraffers.spring.member.model.dao.MemberMapper;
import com.ohgiraffers.spring.member.model.dto.MemberDTO;

@Service
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	public MemberServiceImpl(MemberMapper mapper, BCryptPasswordEncoder passwordEncoder) {
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}
	
	/**
	 * <pre>
	 * 	아이디로 회원 조회용 메소드
	 * </pre>
	 * @param userId 가입용 아이디
	 */
	@Override
	public boolean selectMemberById(String userId) {
		
		String result = mapper.selectMemberById(userId);
		
		return result != null? true : false;
	}

	/**
	 * <pre>
	 * 		회원 가입용 메소드
	 * </pre>
	 * @param member 회원 가입 정보
	 * @throws MemberRegistException 
	 */
	@Override
	public void registMember(MemberDTO member) throws MemberRegistException {
		
		int result = mapper.insertMember(member);
		
		if(!(result > 0)) {
			throw new MemberRegistException("회원 가입에 실패하였습니다.");
		}
	}

	/**
	 * <pre>
	 * 	회원 로그인용 메소드
	 * </pre>
	 * @param member 로그인 정보(아이디,비밀번호)
	 * @throws LoginFailedException 
	 */
	@Override
	public MemberDTO findMember(MemberDTO member) throws LoginFailedException {
		
		System.out.println("check : " + passwordEncoder.matches(member.getPwd(), mapper.selectEncryptedPwd(member)));
		
		if(!passwordEncoder.matches(member.getPwd(), mapper.selectEncryptedPwd(member))) {
			
			throw new LoginFailedException("로그인 실패!");
		}
		
		return mapper.selectMember(member);
	}

	/**
	 * <pre>
	 *   회원 정보 수정용 메소드
	 * </pre>
	 * @param member 회원정보 수정용 데이터
	 * @throws MemberModifyException 
	 */
	@Override
	public void modifyMember(MemberDTO member) throws MemberModifyException {
		
		int result = mapper.updateMember(member);
		
		if(!(result > 0)) {
			throw new MemberModifyException("회원 정보 수정에 실패하셨습니다.");
		}
	}

	/**
	 * <pre>
	 * 	 회원 탈퇴용 메소드
	 * </pre>
	 * @param member 아이디
	 * @throws MemberRegistException 
	 */
	@Override
	public void removeMember(MemberDTO member) throws MemberRemoveException{
		
		int result = mapper.deleteMember(member);
		
		if(!(result > 0)) {
			throw new MemberRemoveException("회원 탈퇴에 실패하였습니다.");
		}
	}

}


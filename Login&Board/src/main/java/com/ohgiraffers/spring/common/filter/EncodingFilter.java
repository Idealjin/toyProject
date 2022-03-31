package com.ohgiraffers.spring.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter{

	private String encodingType;
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		
		/* web.xml에 설정한 init-param의 key를 이용하여 값을 추출*/
		encodingType = fConfig.getInitParameter("encoding-type");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		/* post 요청에 대한 utf-8 인코딩 처리 */
		HttpServletRequest hrequest = (HttpServletRequest) request;
		if("POST".equals(hrequest.getMethod())) {
			request.setCharacterEncoding(encodingType);
			System.out.println("변경된 인코딩 타입 : " + request.getCharacterEncoding());
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {	}

}

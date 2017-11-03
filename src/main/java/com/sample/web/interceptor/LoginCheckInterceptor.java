package com.sample.web.interceptor;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sample.user.vo.User;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

	private Set<String> urlSet;
	public void setUrlSet(Set<String> urlSet) {
		this.urlSet = urlSet;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		//System.out.println("인터셉트의 preHandle()이 실행됨.");
		// getRequestURI() -> 내가 지금 요청한 uri 반환
		String requestURI = request.getRequestURI();
		if (urlSet.contains(requestURI)) {
			//로그인 필요 없으니까 true반환시켜 그 페이지 실행할 수 있게 설정
			return true;
		}
		
		HttpSession session = request.getSession(false);
		if(session == null) {
			response.sendRedirect("/user/login.do?error=deny");
			return false;
		}
		
		User user = (User) session.getAttribute("LOGIN_USER");
		if (user == null) {
			response.sendRedirect("/user/login.do?error=deny");
			return false;
		} else {
			return true;
		}
		
	}
}

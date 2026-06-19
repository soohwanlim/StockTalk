package com.mycom.myapp.common;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mycom.myapp.user.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	// 통과 여부를 true, false 로 리턴
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String requestURI = request.getRequestURI();
		System.out.println("LoginInterceptor >> preHandle : " + requestURI);
		
		HttpSession session = request.getSession();
		UserDto userDto = (UserDto) session.getAttribute("userDto");
		
		if(userDto == null) {
			System.out.println("LoginInterceptor >> preHandle : 로그인 페이지로 이동 (sessionId=" + session.getId() + ")");
			response.sendRedirect(request.getContextPath() + "/pages/login");
			return false;
		}
		
		return true;
	}
}

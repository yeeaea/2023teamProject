package com.pet.security.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler{

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		if (authentication != null) {
            String username = authentication.getName();
            // 로그아웃 성공 이벤트 처리
            System.out.println("로그아웃 성공: " + username);
        } else {
            // authentication이 null인 경우 처리
            System.out.println("로그아웃 성공: 인증되지 않은 사용자");
        }
		
		response.sendRedirect("/"); // 로그아웃 후 리다이렉션할 페이지 지정
	}

}

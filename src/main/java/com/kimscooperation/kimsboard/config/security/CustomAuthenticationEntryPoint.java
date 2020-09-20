package com.kimscooperation.kimsboard.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * JWT토큰 없이 API를 호출하거나, 유효하지 못한 토큰을 가지고 API를 호출했을 때 예외를 처리하기위해 사용하는 컴포넌트<br>
 * Spring Security의 AuthenticationEntryPoint를 구현하여 예외처리 사용
 * @author JunkiKim
 *
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException ex) throws IOException, ServletException {
		System.out.println(request.getRequestURI());
		response.sendRedirect("/exception/entrypoint");
	}
}
package com.kimscooperation.kimsboard.config.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * 자신의 권한 이외의 리소스에 접근할 때 발생하는 오류를 잡기위한 컴포넌트<br>
 * Spring Security의 AccessDeniedHandler를 구현하고 커스텀화 하여서 사용합니다.<br>
 * 
 * <pre>
 * /excetion/accessdenied> url로 redirect하여 예외를 처리함
 * </pre>
 * 
 * @author JunkiKim
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		response.sendRedirect("/exception/accessdenied");
	}
}
package com.kimscooperation.kimsboard.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kimscooperation.kimsboard.advice.exception.CAuthenticationEntryPointException;
import com.kimscooperation.kimsboard.advice.exception.CIdSigninFailedException;
import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.service.ResponseService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	private final ResponseService responseService;
	private final MessageSource messageSource;

	/**
	 * @exception 최상위 예외처리 객체(Exception.class)로 다른 Exception 핸들러에서 처리되지 않으면 기본적으로
	 *                처리<br>
	 * @ResponseStatus 위의 Exception이 발생하면 HttpStatus code를
	 *                 INTERNAL_SERVER_ERROR(500)로 응답<br>
	 * @param request
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult defaultException(HttpServletRequest request, Exception exception) {
		System.out.println("요청 URI : " + request.getRequestURL());
		// 오류가나면 responseService의 getFailResult()메소드를 통해 상태메세지 외에 더 상세한 오류 정보를 주기 위해
		return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
	}

	@ExceptionHandler(CUserNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
		System.out.println(getMessage("userNotFound.code"));

		return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")),
				getMessage("userNotFound.msg"));
	}

	@ExceptionHandler(CIdSigninFailedException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult idSigninFailedException() {
		System.out.println(getMessage("idSigninFailedException.code"));

		return responseService.getFailResult(Integer.valueOf(getMessage("idSigninFailed.code")),
				getMessage("idSigninFailed.msg"));
	}

	@ExceptionHandler(CAuthenticationEntryPointException.class)
	public CommonResult authenticationEntryPointException(HttpServletRequest request,
			CAuthenticationEntryPointException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")),
				getMessage("entryPointException.msg"));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")),
				getMessage("accessDenied.msg"));
	}

	// code정보에 해당하는 메시지를 조회합니다.
	private String getMessage(String code) {
		return getMessage(code, null);
	}

	// code정보, 추가 argument로 현재 locale에 맞는 메시지를 조회합니다.
	private String getMessage(String code, Object[] args) {
		return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
	}

}

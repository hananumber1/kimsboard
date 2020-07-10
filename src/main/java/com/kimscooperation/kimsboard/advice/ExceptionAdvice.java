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
import com.kimscooperation.kimsboard.advice.exception.CCommunicationException;
import com.kimscooperation.kimsboard.advice.exception.CIdSigninFailedException;
import com.kimscooperation.kimsboard.advice.exception.CNotOwnerException;
import com.kimscooperation.kimsboard.advice.exception.CResourceNotExistException;
import com.kimscooperation.kimsboard.advice.exception.CUserExistException;
import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.service.ResponseService;

import lombok.RequiredArgsConstructor;

/**
 * ControllerAdvice를 통해서 전역에서 발생한 에러를 처리 해주는 공통의 예외처리 클래스<br>
 * 각 에러에 해당하는 예외가 발생하면 공통의 응답처리 responseService의 로직을 가지고 표준 응답(성공 여부,예외 코드,예외
 * 메시지)를 반환 합니다.
 * 
 * @author JunkiKim
 */
@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionAdvice {

	/**
	 * 표준 응답 service
	 */
	private final ResponseService responseService;
	/**
	 * .properties에 저장된 code와 message를 불러오기 위한 인터페이스
	 */
	private final MessageSource messageSource;

	/**
	 * 아래에 정의된 에러 중 해당하는 에러가 없을때 최상위 예외처리 클래스인 Exception 클래스로 잡습니다.
	 * 
	 * @param request
	 * @param exception
	 * @return response.getFailResult(오류 코드, 오류메시지)를 표준화 하여 전달
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult defaultException(HttpServletRequest request, Exception exception) {
		return responseService.getFailResult(Integer.valueOf(getMessage("unKnown.code")), getMessage("unKnown.msg"));
	}

	@ExceptionHandler(CUserNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult userNotFoundException(HttpServletRequest request, CUserNotFoundException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("userNotFound.code")), getMessage("userNotFound.msg"));
	}

	@ExceptionHandler(CIdSigninFailedException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	protected CommonResult idSigninFailedException() {
		return responseService.getFailResult(Integer.valueOf(getMessage("idSigninFailed.code")), getMessage("idSigninFailed.msg"));
	}

	@ExceptionHandler(CAuthenticationEntryPointException.class)
	public CommonResult authenticationEntryPointException(HttpServletRequest request, CAuthenticationEntryPointException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("entryPointException.code")), getMessage("entryPointException.msg"));
	}

	@ExceptionHandler(AccessDeniedException.class)
	public CommonResult AccessDeniedException(HttpServletRequest request, AccessDeniedException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("accessDenied.code")), getMessage("accessDenied.msg"));
	}

	@ExceptionHandler(CCommunicationException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult communicationException(HttpServletRequest request, CCommunicationException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("communicationError.code")), getMessage("communicationError.msg"));
	}

	@ExceptionHandler(CUserExistException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public CommonResult communicationException(HttpServletRequest request, CUserExistException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("existingUser.code")), getMessage("existingUser.msg"));
	}

	@ExceptionHandler(CNotOwnerException.class)
	@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
	public CommonResult notOwnerException(HttpServletRequest request, CNotOwnerException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("notOwner.code")), getMessage("notOwner.msg"));
	}

	@ExceptionHandler(CResourceNotExistException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public CommonResult resourceNotExistException(HttpServletRequest request, CResourceNotExistException e) {
		return responseService.getFailResult(Integer.valueOf(getMessage("resourceNotExist.code")), getMessage("resourceNotExist.msg"));
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

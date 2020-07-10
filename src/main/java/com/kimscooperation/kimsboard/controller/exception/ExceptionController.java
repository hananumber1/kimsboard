package com.kimscooperation.kimsboard.controller.exception;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kimscooperation.kimsboard.advice.exception.CAuthenticationEntryPointException;
import com.kimscooperation.kimsboard.model.CommonResult;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
	
	@RequestMapping(value = "/entrypoint", method = RequestMethod.GET)
	public CommonResult entrypointException() {
		throw new CAuthenticationEntryPointException();
	}
	
	@RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
	public CommonResult accessdeniedException() {
		throw new AccessDeniedException("");
	}
}
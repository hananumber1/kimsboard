package com.kimscooperation.kimsboard.controller.v1;

import java.util.Collections;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kimscooperation.kimsboard.advice.exception.CIdSigninFailedException;
import com.kimscooperation.kimsboard.config.security.JwtTokenProvider;
import com.kimscooperation.kimsboard.domain.Users;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.model.SingleResult;
import com.kimscooperation.kimsboard.repository.UserRepository;
import com.kimscooperation.kimsboard.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1. Sign" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class SignController {

	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ResponseService responseService;
	private final PasswordEncoder passwordEncoder;

	@ApiOperation(value = "로그인", notes = "회원 로그인을 한다.")
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	public SingleResult<String> signin(@ApiParam(value = "회원ID ", required = true) @RequestParam String userId,
			@ApiParam(value = "비밀번호", required = true) @RequestParam String password) {
		Users user = userRepository.findByUserId(userId).orElseThrow(CIdSigninFailedException::new);
		if (!passwordEncoder.matches(password, user.getPassword()))
			throw new CIdSigninFailedException();
		return responseService
				.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserNum()), user.getRoles()));
	}

	@ApiOperation(value = "가입", notes = "회원가입을 한다.")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public CommonResult signin(@ApiParam(value = "회원ID", required = true) @RequestParam String userId,
			@ApiParam(value = "비밀번호", required = true) @RequestParam String password,
			@ApiParam(value = "이름", required = true) @RequestParam String name) {
		userRepository.save(Users.builder().userId(userId).password(passwordEncoder.encode(password)).name(name)
				.roles(Collections.singletonList("ROLE_USER")).build());
		return responseService.getSuccessResult();
	}

}

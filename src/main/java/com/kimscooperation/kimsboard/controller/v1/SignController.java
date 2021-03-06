package com.kimscooperation.kimsboard.controller.v1;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kimscooperation.kimsboard.advice.exception.CIdSigninFailedException;
import com.kimscooperation.kimsboard.advice.exception.CUserExistException;
import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.config.security.JwtTokenProvider;
import com.kimscooperation.kimsboard.entity.user.Users;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.model.KakaoProfile;
import com.kimscooperation.kimsboard.model.SingleResult;
import com.kimscooperation.kimsboard.model.user.ParamsJoin;
import com.kimscooperation.kimsboard.model.user.ParamsUser;
import com.kimscooperation.kimsboard.repository.UserRepository;
import com.kimscooperation.kimsboard.service.ResponseService;
import com.kimscooperation.kimsboard.service.social.KakaoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
	private final KakaoService kakaoService;

	@ApiOperation(value = "로그인", notes = "일반 로그인을 처리하는 API")
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
		public SingleResult<Map<String, Object>> signin(@Valid @RequestBody ParamsUser paramsUser) {
		String userId = paramsUser.getUserId();
		String password = paramsUser.getPassword();
		Users user = userRepository.findByUserId(userId).orElseThrow(CIdSigninFailedException::new);
		if (!passwordEncoder.matches(password, user.getPassword()))
			throw new CIdSigninFailedException();
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("userToken",jwtTokenProvider.createToken(String.valueOf(user.getUserNum()), user.getRoles()));
		resultMap.put("user",user);
		return responseService.getSingleResult(resultMap);
	}

	@ApiOperation(value = "아이디 중복확인", notes = "받아온 아이디 값으로 회원이 존재하는 확인합니다.")
	@ApiResponses(value={
		@ApiResponse(code = 200, message = "data의 값이 true이면 중복된 아이디 있음 false면 없음")
	})
	@RequestMapping(value = "/user/userid", method = RequestMethod.GET)
	public CommonResult checkid(@RequestParam String userId){
		return responseService.getSingleResult(userRepository.findByUserId(userId).isPresent());
	}

	@ApiOperation(value = "가입", notes = "비회원이 회원에 가입하기 위한 요청을 처리하는 API")
	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public CommonResult signup(@Valid @RequestBody ParamsJoin paramsJoin) {
		userRepository.save(Users.builder().userId(paramsJoin.getUserId())
				.password(passwordEncoder.encode(paramsJoin.getPassword())).name(paramsJoin.getName())
				.phone(paramsJoin.getPhone()).address(paramsJoin.getAddress())
				.roles(Collections.singletonList("ROLE_USER")).build()); //한개의 요소를 가지고 List를 사용한다면 왼쪽 처럼 사용. 원래코드는 Arrays.asList("ROLE_USER); 
		return responseService.getSuccessResult();
	}

	@ApiOperation(value = "소셜 로그인", notes = "소셜 회원 로그인을 한다.")
	@RequestMapping(value = "/signin/{provider}", method = RequestMethod.POST)
	public SingleResult<String> signinByProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider,
			@ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken) {
		KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
		Users user = userRepository.findByUserIdAndProvider(String.valueOf(profile.getId()), provider).orElseThrow(CUserNotFoundException::new);
		return responseService.getSingleResult(jwtTokenProvider.createToken(String.valueOf(user.getUserNum()), user.getRoles()));
	}

	@ApiOperation(value = "소셜 계정 가입", notes = "소셜 계정 회원가입을 한다.")
	@RequestMapping(value = "/signup/{provider}", method = RequestMethod.POST)
	public CommonResult signupProvider(@ApiParam(value = "서비스 제공자 provider", required = true, defaultValue = "kakao") @PathVariable String provider, @ApiParam(value = "소셜 access_token", required = true) @RequestParam String accessToken,
			@ApiParam(value = "이름", required = true) @RequestParam String name) {
		KakaoProfile profile = kakaoService.getKakaoProfile(accessToken);
		Optional<Users> user = userRepository.findByUserIdAndProvider(String.valueOf(profile.getId()), provider);
		if (user.isPresent())
			throw new CUserExistException();
		userRepository.save(Users.builder().userId(String.valueOf(profile.getId())).provider(provider).name(name).roles(Collections.singletonList("ROLE_USER")).build());
		return responseService.getSuccessResult();
	}

}

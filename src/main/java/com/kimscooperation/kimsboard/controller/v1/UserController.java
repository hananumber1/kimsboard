package com.kimscooperation.kimsboard.controller.v1;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.domain.Users;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.model.ListResult;
import com.kimscooperation.kimsboard.model.SingleResult;
import com.kimscooperation.kimsboard.repository.UserRepository;
import com.kimscooperation.kimsboard.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "2. Users" })
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@RestController
public class UserController {

	private final UserRepository userRepository;
	private final ResponseService responseService; // 결과를 처리할 Service

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 리스트 조회", notes = "모든 회원을 조회한다")
	@RequestMapping(value = "/users",method = RequestMethod.GET)
	public ListResult<Users> findAllUser() {
		// 결과데이터가 여러건인경우 getListResult를 이용해서 결과를 출력한다.
		return responseService.getListResult(userRepository.findAll());
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = false, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 단건 조회", notes = "회원번호(msrl)로 회원을 조회한다")
	@RequestMapping(value = "/user" , method = RequestMethod.GET)
	public SingleResult<Users> findUserById(@ApiParam(value = "언어", defaultValue = "ko") @RequestParam String lang) {
		// SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId= authentication.getName();
		System.out.println("userId= "+ userId);
		// 결과데이터가 단일건인경우 getSingleResult를 이용해서 결과를 출력한다.
		return responseService.getSingleResult(userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new));
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다")
	@RequestMapping(value = "/user", method = RequestMethod.PUT)
	public SingleResult<Users> modify(@ApiParam(value = "회원번호", required = true) @RequestParam int userNum,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name) {
		Users user = Users.builder().userNum(userNum).name(name).build();
		return responseService.getSingleResult(userRepository.save(user));
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
	@RequestMapping(value = "/user/{userNum}", method = RequestMethod.DELETE)
	public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable Long userNum) {
		userRepository.deleteById(userNum);
		// 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
		return responseService.getSuccessResult();
	}
}

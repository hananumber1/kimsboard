package com.kimscooperation.kimsboard.controller.v1;

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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1. Users" })
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
@RestController
public class UserController {

	private final UserRepository userRepository;
	private final ResponseService responseService; // 결과를 처리할 Service

	@ApiOperation(value = "회원 추가", notes = "회원을 추가합니다.")
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public SingleResult<Users> addOneUser(@ApiParam(value = "회원아이디", required = true) @RequestParam String userId,
			@ApiParam(value = "비밀번호", required = true) @RequestParam String password,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name) {
		Users user = Users.builder().userId(userId).password(password).name(name).build();
		return responseService.getSingleResult(userRepository.save(user));
	}

	@ApiOperation(value = "회원 리스트 조회", notes = "전체 회원 리스트를 조회합니다.")
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public ListResult<Users> findAllUser() {
		return responseService.getListResult(userRepository.findAll());
	}

	@ApiOperation(value = "회원 단건 조회", notes = "회원 번호(userNum)로 회원 정보를 조회합니다.")
	@RequestMapping(value = "/user/{userNum}", method = RequestMethod.GET)
	public SingleResult<Users> findUserByUserNum(@ApiParam(value = "회원 번호", required = true) @PathVariable long userNum) {
		return responseService.getSingleResult(userRepository.findById(userNum).orElseThrow(CUserNotFoundException::new));
	}

	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정합니다.")
	@RequestMapping(value = "/user",method = RequestMethod.PUT)
	public SingleResult<Users> modify(@ApiParam(value = "회원 번호", required = true) @RequestParam long userNum,
			@ApiParam(value = "회원 아이디", required = true) @RequestParam String userId,
			@ApiParam(value = "회원 비밀번호", required = true) @RequestParam String password,
			@ApiParam(value = "회원 이름", required = true) @RequestParam String name) {
		Users user = Users.builder().userNum(userNum).userId(userId).password(password).name(name).build();
		return responseService.getSingleResult(userRepository.save(user));
	}

	@ApiOperation(value = "회원 삭제", notes = "userId로 회원정보를 삭제한다")
	@RequestMapping(value = "/user/{userNum}", method = RequestMethod.DELETE)
	public CommonResult delete(@ApiParam(value = "회원번호", required = true) @PathVariable long userNum) {
		userRepository.deleteById(userNum);
		// 성공 결과 정보만 필요한경우 getSuccessResult()를 이용하여 결과를 출력한다.
		return responseService.getSuccessResult();
	}

}

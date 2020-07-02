package com.kimscooperation.kimsboard.controller.v1;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kimscooperation.kimsboard.domain.User;
import com.kimscooperation.kimsboard.repository.UserRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = { "1. User" })
@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/v1")
public class UserController {

	private UserRepository userRepository;

	@ApiOperation(value = "회원 입력", notes = "회원을 입력합니다.")
	@RequestMapping(value="/user", method = RequestMethod.POST)
	public User addOneUser(@ApiParam(value = "회원아이디", required = true) @RequestParam String userId,
			@ApiParam(value = "비밀번호", required = true) @RequestParam String password,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name) {
		User user1 = User.builder().userId(userId).password(password).name(name).build();
		return userRepository.save(user1);
	}

	@ApiOperation(value = "전 회원 조회", notes = "모든 회원을 조회합니다.")
	@RequestMapping(value="/user", method = RequestMethod.GET)
	public List<User> findAllUser() {
		return userRepository.findAll();
	}

}

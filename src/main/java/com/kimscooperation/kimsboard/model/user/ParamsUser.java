package com.kimscooperation.kimsboard.model.user;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "사용자 파라미터")
public class ParamsUser {
    
	@NotEmpty
	@ApiModelProperty(value = "사용자 아이디", required = true)
	private String userId;
	
	@NotEmpty
	@ApiModelProperty(value = "사용자 비밀번호", required = true)
    private String password;
    
}
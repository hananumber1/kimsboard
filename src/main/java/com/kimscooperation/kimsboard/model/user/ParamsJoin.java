package com.kimscooperation.kimsboard.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@ApiModel(value = "가입 파라미터")
public class ParamsJoin {
    
    @ApiModelProperty(value="사용자 아이디", required = true)
    String userId;
    
    @ApiModelProperty(value="비밀 번호", required = true)
    String password;

    @ApiModelProperty(value="이름", required = false)
    String name;
    
    @ApiModelProperty(value="주소", required = false)
    String address;

    @ApiModelProperty(value="휴대폰 번호", required = false)
    String phone;
}
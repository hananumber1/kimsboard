package com.kimscooperation.kimsboard.model.board;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParamsWriteReply {
    
	@Size(min = 2, max = 500)
	@ApiModelProperty(value = "내용", required = true)
    private String content;
    
}

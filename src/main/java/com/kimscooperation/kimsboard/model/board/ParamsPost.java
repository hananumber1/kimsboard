package com.kimscooperation.kimsboard.model.board;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParamsPost {

	@NotEmpty
	@ApiModelProperty(value = "작성자명", required = true)
	private String writer;
	
	@NotEmpty
	@Size(min = 2, max = 100)
	@ApiModelProperty(value = "제목", required = true)
	private String title;
	
	@Size(min = 2, max = 500)
	@ApiModelProperty(value = "내용", required = true)
	private String content;
}
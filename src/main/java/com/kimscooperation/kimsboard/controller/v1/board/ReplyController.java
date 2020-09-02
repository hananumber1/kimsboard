package com.kimscooperation.kimsboard.controller.v1.board;

import javax.validation.Valid;

import com.kimscooperation.kimsboard.entity.board.Reply;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.model.SingleResult;
import com.kimscooperation.kimsboard.model.board.ParamsWriteReply;
import com.kimscooperation.kimsboard.service.ResponseService;
import com.kimscooperation.kimsboard.service.board.ReplyService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = { "4. Reply"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/reply")
public class ReplyController {

    private final ReplyService replyService;
    private final ResponseService responseService;
    

    @ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "댓글 작성", notes = "글에 댓글을 작성한다.")
	@RequestMapping(value = "/{postId}", method = RequestMethod.POST)
	public SingleResult<Reply> post(@PathVariable Long postId, @Valid @RequestBody ParamsWriteReply writeReply) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		return responseService.getSingleResult(replyService.writeReply(Long.parseLong(uid), postId, writeReply));
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "댓글 삭제", notes = "게시글의 댓글을 삭제한다.")
	@DeleteMapping(value = "/{replyId}")
	public CommonResult deletePost(@PathVariable long replyId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		replyService.deleteReply(replyId, uid);
		return responseService.getSuccessResult();
	}
    
}
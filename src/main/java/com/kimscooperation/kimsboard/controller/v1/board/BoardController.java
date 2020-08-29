package com.kimscooperation.kimsboard.controller.v1.board;

import javax.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kimscooperation.kimsboard.entity.board.Board;
import com.kimscooperation.kimsboard.entity.board.Post;
import com.kimscooperation.kimsboard.model.CommonResult;
import com.kimscooperation.kimsboard.model.ListResult;
import com.kimscooperation.kimsboard.model.SingleResult;
import com.kimscooperation.kimsboard.model.board.ParamsPost;
import com.kimscooperation.kimsboard.model.board.ParamsWritePost;
import com.kimscooperation.kimsboard.service.ResponseService;
import com.kimscooperation.kimsboard.service.board.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@Api(tags = { "3. Board" })
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/board")
public class BoardController {

	private final BoardService boardService;
	private final ResponseService responseService;

	@ApiImplicitParams({@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")})
	@ApiOperation(value = "게시판 생성", notes = "신규 게시판을 생성한다.")
	@PostMapping(value = "/{boardName}")
	public SingleResult<Board> createBoard(@PathVariable String boardName) {
		return responseService.getSingleResult(boardService.insertBoard(boardName));
	}

	@ApiOperation(value = "게시판 정보 조회", notes = "게시판 정보를 조회한다.")
	@GetMapping(value = "/{boardName}")
	public SingleResult<Board> boardInfo(@PathVariable String boardName) {
		return responseService.getSingleResult(boardService.findBoard(boardName));
	}

	@ApiOperation(value = "게시판 글 리스트", notes = "게시판 게시글 리스트를 조회한다.")
	@GetMapping(value = "/{boardName}/posts")
	public ListResult<Post> posts(@PathVariable String boardName) {
		return responseService.getListResult(boardService.findPosts(boardName));
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "게시판 글 작성", notes = "게시판에 글을 작성한다.")
	@PostMapping(value = "/{boardName}/post")
	public SingleResult<Post> post(@PathVariable String boardName, @Valid @RequestBody ParamsWritePost post) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		System.out.println(boardName);
		System.out.println(post);
		System.out.println(uid);
		return responseService.getSingleResult(boardService.writePost(Long.parseLong(uid), boardName, post));
	}

	@ApiOperation(value = "게시판 글 상세", notes = "게시판 글 상세정보를 조회한다.")
	@GetMapping(value = "/post/{postId}")
	public SingleResult<Post> post(@PathVariable long postId) {
		return responseService.getSingleResult(boardService.getPost(postId));
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "게시판 글 수정", notes = "게시판의 글을 수정한다.")
	@PutMapping(value = "/post/{postId}")
	public SingleResult<Post> post(@PathVariable long postId, @Valid @ModelAttribute ParamsPost post) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		return responseService.getSingleResult(boardService.updatePost(postId, uid, post));
	}

	@ApiImplicitParams({ @ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header") })
	@ApiOperation(value = "게시판 글 삭제", notes = "게시판의 글을 삭제한다.")
	@DeleteMapping(value = "/post/{postId}")
	public CommonResult deletePost(@PathVariable long postId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String uid = authentication.getName();
		boardService.deletePost(postId, uid);
		return responseService.getSuccessResult();
	}
}
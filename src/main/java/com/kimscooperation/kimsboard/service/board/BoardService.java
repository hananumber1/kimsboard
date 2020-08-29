package com.kimscooperation.kimsboard.service.board;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kimscooperation.kimsboard.advice.exception.CNotOwnerException;
import com.kimscooperation.kimsboard.advice.exception.CResourceNotExistException;
import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.entity.board.Board;
import com.kimscooperation.kimsboard.entity.board.Post;
import com.kimscooperation.kimsboard.entity.user.Users;
import com.kimscooperation.kimsboard.model.board.ParamsPost;
import com.kimscooperation.kimsboard.model.board.ParamsWritePost;
import com.kimscooperation.kimsboard.repository.BoardRepository;
import com.kimscooperation.kimsboard.repository.PostRepository;
import com.kimscooperation.kimsboard.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository boardRepository;
	private final PostRepository postRepository;
	private final UserRepository userRepository;

	//신규게시판을 추가한다.
	public Board insertBoard(final String boardName) {
        return boardRepository.save(Board.builder().name(boardName).build());
    }

	// 게시판 이름으로 게시판을 조회. 없을경우 CResourceNotExistException 처리
	public Board findBoard(final String boardName) {
		return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(CResourceNotExistException::new);
	}

	// 게시판 이름으로 게시물 리스트 조회.
	public List<Post> findPosts(final String boardName) {
		return postRepository.findByBoard(findBoard(boardName));
	}

	// 게시물ID로 게시물 단건 조회. 없을경우 CResourceNotExistException 처리
	public Post getPost(final long postId) {
		return postRepository.findById(postId).orElseThrow(CResourceNotExistException::new);
	}

	// 게시물을 등록합니다. 게시물의 회원userId가 조회되지 않으면 CUserNotFoundException 처리합니다.
	public Post writePost(final Long userNum, final String boardName, final ParamsWritePost ParamsWritePost) {
		final Board board = findBoard(boardName);
		final Users user = userRepository.findByUserNum(userNum).orElseThrow(CUserNotFoundException::new);
		final Post post = new Post(user, board, user.getUsername(), ParamsWritePost.getTitle(), ParamsWritePost.getContent());
		return postRepository.save(post);
	}

	// 게시물을 수정합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
	public Post updatePost(final long postId, final String userNum, final ParamsPost paramsPost) {
		final Post post = getPost(postId);
		final Users user = post.getUser();
		if(! (user.getUserNum() == Long.parseLong(userNum))){
			throw new CNotOwnerException();
		}
		// 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Post내용을 변경만 해도 Update쿼리가 실행됩니다.
		post.setUpdate(post.getWriter(), paramsPost.getTitle(), paramsPost.getContent());
		return post;
	}

	// 게시물을 삭제합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
	public boolean deletePost(final long postId, final String userId) {
		final Post post = getPost(postId);
		final Users user = post.getUser();
		if (!userId.equals(user.getUserId())) {
			throw new CNotOwnerException();
		}
		postRepository.delete(post);
		return true;
	}
}
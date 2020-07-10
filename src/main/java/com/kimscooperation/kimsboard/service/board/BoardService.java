package com.kimscooperation.kimsboard.service.board;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kimscooperation.kimsboard.advice.exception.CNotOwnerException;
import com.kimscooperation.kimsboard.advice.exception.CResourceNotExistException;
import com.kimscooperation.kimsboard.advice.exception.CUserNotFoundException;
import com.kimscooperation.kimsboard.domain.board.Board;
import com.kimscooperation.kimsboard.domain.board.Post;
import com.kimscooperation.kimsboard.domain.user.Users;
import com.kimscooperation.kimsboard.model.board.ParamsPost;
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

	// 게시판 이름으로 게시판을 조회. 없을경우 CResourceNotExistException 처리
	public Board findBoard(String boardName) {
		return Optional.ofNullable(boardRepository.findByName(boardName)).orElseThrow(CResourceNotExistException::new);
	}

	// 게시판 이름으로 게시물 리스트 조회.
	public List<Post> findPosts(String boardName) {
		return postRepository.findByBoard(findBoard(boardName));
	}

	// 게시물ID로 게시물 단건 조회. 없을경우 CResourceNotExistException 처리
	public Post getPost(long postId) {
		return postRepository.findById(postId).orElseThrow(CResourceNotExistException::new);
	}

	// 게시물을 등록합니다. 게시물의 회원userId가 조회되지 않으면 CUserNotFoundException 처리합니다.
	public Post writePost(String userId, String boardName, ParamsPost paramsPost) {
		Board board = findBoard(boardName);
		Post post = new Post(userRepository.findByUserId(userId).orElseThrow(CUserNotFoundException::new), board, paramsPost.getWriter(), paramsPost.getTitle(), paramsPost.getContent());
		return postRepository.save(post);
	}

	// 게시물을 수정합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
	public Post updatePost(long postId, String userId, ParamsPost paramsPost) {
		Post post = getPost(postId);
		Users user = post.getUser();
		if (!userId.equals(user.getUserId())) {
			throw new CNotOwnerException();
		}
		// 영속성 컨텍스트의 변경감지(dirty checking) 기능에 의해 조회한 Post내용을 변경만 해도 Update쿼리가 실행됩니다.
		post.setUpdate(paramsPost.getWriter(), paramsPost.getTitle(), paramsPost.getContent());
		return post;
	}

	// 게시물을 삭제합니다. 게시물 등록자와 로그인 회원정보가 틀리면 CNotOwnerException 처리합니다.
	public boolean deletePost(long postId, String userId) {
		Post post = getPost(postId);
		Users user = post.getUser();
		if (!userId.equals(user.getUserId())) {
			throw new CNotOwnerException();
		}
		postRepository.delete(post);
		return true;
	}
}
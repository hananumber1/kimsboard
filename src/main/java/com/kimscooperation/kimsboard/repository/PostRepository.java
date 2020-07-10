package com.kimscooperation.kimsboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kimscooperation.kimsboard.domain.board.Board;
import com.kimscooperation.kimsboard.domain.board.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByBoard(Board board);
}

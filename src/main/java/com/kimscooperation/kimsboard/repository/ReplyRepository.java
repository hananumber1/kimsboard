package com.kimscooperation.kimsboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kimscooperation.kimsboard.entity.board.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Long> {
}

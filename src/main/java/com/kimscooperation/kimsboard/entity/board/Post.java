package com.kimscooperation.kimsboard.entity.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.kimscooperation.kimsboard.entity.common.CommonDateEntity;
import com.kimscooperation.kimsboard.entity.user.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends CommonDateEntity {

	@Id
	@Column(name = "post_id")
	@SequenceGenerator(name = "post_seq_generator", sequenceName = "post_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_seq_generator")
	private Long postId;

	@Column(nullable = false, length = 50)
	private String writer;

	@Column(nullable = false, length = 100)
	private String title;

	@Column(length = 500)
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private Board board; // 게시글 - 게시판의 관계 - N:1

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_num")
	private Users user; // 게시글 - 회원의 관계 - N:1

	@OneToMany(mappedBy = "post" ,fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<Reply> replies = new ArrayList<Reply>();

	// Join 테이블이 Json결과에 표시되지 않도록 처리.
	protected Board getBoard() {
		return board;
	}

	// 생성자
	public Post(Users user, Board board, String writer, String title, String content) {
		this.user = user;
		this.board = board;
		this.writer = writer;
		this.title = title;
		this.content = content;
	}

	// 수정시 데이터 처리
	public Post setUpdate(String writer, String title, String content) {
		this.writer = writer;
		this.title = title;
		this.content = content;
		return this;
	}
}
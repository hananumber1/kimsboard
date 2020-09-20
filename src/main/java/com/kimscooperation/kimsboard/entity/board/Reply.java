package com.kimscooperation.kimsboard.entity.board;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kimscooperation.kimsboard.entity.common.CommonDateEntity;
import com.kimscooperation.kimsboard.entity.user.Users;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "reply")
public class Reply extends CommonDateEntity {

    @Id
    @Column(name = "reply_id")
    @SequenceGenerator(name = "reply_seq_generator", sequenceName = "reply_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reply_seq_generator")
    private Long replyId;

    @Column(name = "content", nullable = false, length = 50)
    private String content;

    @Column(name = "replyer", nullable = false)
    private String replyer;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_num")
	private Users user; // 댓글 - 회원의 관계 - N:1

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    @JsonBackReference
    private Post post;

    public Reply(String content, String replyer, Post post, Users user) {
        this.content = content;
        this.replyer = replyer;
        this.post = post;
        this.user = user;
    }

}
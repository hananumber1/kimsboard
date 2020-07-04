package com.kimscooperation.kimsboard.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@ApiModel(value = "사용자", description = "사용자 엔티티")
public class Users {

	@ApiModelProperty(value = "사용자 번호")
	@Id
	@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
	private long userNum;

	@ApiModelProperty(value = "사용자 아이디")
	@Column(name = "user_id", nullable = false)
	private String userId;

	@ApiModelProperty(value = "사용자 비밀번호")
	@Column(name = "password", nullable = false)
	private String password;

	@ApiModelProperty(value = "사용자 이름")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "사용자 가입일")
	@CreationTimestamp
	@Column(name = "regdate")
	private Date regdate;

}

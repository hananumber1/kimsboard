package com.kimscooperation.kimsboard.domain;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Users implements UserDetails {

	@ApiModelProperty(value = "사용자 번호")
	@Id
	@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
	private long userNum;

	@ApiModelProperty(value = "사용자 아이디")
	@Column(name = "user_id", nullable = false)
	private String userId;

	@ApiModelProperty(value = "사용자 비밀번호")
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;

	@ApiModelProperty(value = "사용자 이름")
	@Column(name = "name")
	private String name;

	@ApiModelProperty(value = "사용자 가입일")
	@CreationTimestamp
	@Column(name = "regdate")
	private Date regdate;

	@Column(length = 100)
	private String provider;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public String getUsername() {
		return this.userId;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {
		return true;
	}

}

package com.kimscooperation.kimsboard.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Users implements UserDetails {

	@Id
	@SequenceGenerator(name = "user_seq_generator", sequenceName = "user_seq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
	@Column(name = "user_num")
	private long userNum;

	@Column(name = "user_id", nullable = false, unique = true)
	private String userId;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //JSON 결과로 출력하지 않을 경우 사용
	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(length = 100, name="provider")
	private String provider;

	@Column(name="phone")
	private String phone;

	@Column(name="address")
	private String address;


	@Builder.Default // roles가 null인 경우에 ArrayList로 초기화
	@ElementCollection(fetch = FetchType.EAGER) //값 타입을 하나이상 보관하기 위해서 씀 
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

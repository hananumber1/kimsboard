package com.kimscooperation.kimsboard.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.httpBasic().disable() // Rest API에서는 기본설정 사용X. *기본설정 : 비인증 상황에서 인증화면으로 redirect
				.csrf().disable() // Rest API에서는 Cookie 같은 정보를 사용하지 않으므로 보안이 필요없다고 생각하여 disable 처리.
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Jwt token으로 인증하므로 세션으로 관리를 할 필요 없음.
				.and().authorizeRequests() // 아래에 있는 request에 대한 권한 및 제한 설정.
				.antMatchers("/*/signin", "/*/signup").permitAll() // 가입 및 인증(로그인) API는 모두 허용
				.antMatchers(HttpMethod.GET, "/exception/**").permitAll() // Jwt토큰이 없거나 비정상(다르거나 만료된)토큰인 경우 예외처리를 위한 URI
				.antMatchers("/*/users").hasRole("ADMIN") // users요청시 admin으로 설정
				.anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 user로 권한을 가진 사람만
				.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).and().exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).and()
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 넣어라.
	}

	@Override // ignore check swagger resource
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}

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
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
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
				.antMatchers("/*/signin", "/*/signin/**", "/*/signup", "/*/signup/**", "/social/**", "/*/user/userid").permitAll() // 가입 및 인증(로그인) API는 모두 허용
				.antMatchers(HttpMethod.GET, "/v1/board/**", "/favicon.ico").permitAll()
				.antMatchers(HttpMethod.GET, "/exception/**").permitAll() // Jwt토큰이 없거나 비정상(다르거나 만료된)토큰인 경우 예외처리를 위한 URI
				.antMatchers("/*/users").hasRole("ADMIN") // users요청시 admin으로 설정
				.anyRequest().hasRole("USER") // 그외 나머지 요청은 모두 user로 권한을 가진 사람만
				.and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()) // 자신의 권한이 아닌 리소스를 요청할 때
				.and().exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()) //잘못된 토큰을 가지고 인증 요청을 했을 때
				.and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class); //JWT 토큰을 이용한 인증과정을 Spring Security Filter로 등록 
	}

	@Override // ignore check swagger resource
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**");
	}

	//PasswordEncoder의 타입을
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}

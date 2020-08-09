package com.kimscooperation.kimsboard.config.security;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.kimscooperation.kimsboard.domain.user.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

/**
 * JWT토큰 생성 및 관리 컴포넌트
 * JWT토큰 생성, 인증, 정보 조회, 토큰 유효성검사, 비밀키 값 설정에 필요한 컴포넌트 입니다. 
 * @author JunkiKim
 */
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

	// secretKey는 Vm arguments로 받아서 사용
	@Value("spring.jwt.secret")
	private String secretKey;

	// 토큰의 유효 시간을 1시간으로 설정. 1시간이후는 새롭게 refresh 해야함.
	//private final long tokenValidMilisecond = 1000L * 60 * 60;
	private final long tokenValidMilisecond = 1000L * 60;


	/**
	 * was가 실행될 때 init()메소드를 실행하고 secretKey를 암호화
	 */
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}

	/**
	 * JWT 토큰을 생성하는 메소드입니다.
	 * @param userNum {@link Users} 사용자의 번호 
	 * @param roles User {@link Users} 사용자의 권한
	 * @return jwt String
	 */
	public String createToken(final String userNum, final List<String> roles) {
		final Claims claims = Jwts.claims().setSubject(userNum);
		claims.put("roles", roles);
		final Date now = new Date();
		
		//Jwt payload 담을 내용을 builder를 통해 만들고 반환
		return Jwts.builder()
				.setClaims(claims) // userNum과 role을 이름:값으로 저장
				.setIssuedAt(now) // 토큰의 발금된 시간
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond)) // 토큰의 만료 기간
				.signWith(SignatureAlgorithm.HS256, secretKey) // 토큰의 암호화 알고리즘과 비밀키
				.compact();
	}
	
	public Authentication getAuthentication(final String token) {
		//토큰으로부터 정보를 가져옴.
		final Map<String, Object> parseMap = getUserInfo(token);
		final Users user = Users.builder().userId(String.valueOf(parseMap.get("userNum"))).roles((List<String>)parseMap.get("authorities")).build();
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	/**
	 * jwt token의 body에서 claims로 부터 user정보를 가져옵니다.
	 * @param token Jwt token
	 * @return
	 */
    public Map<String, Object> getUserInfo(final String token) {
        final Jws<Claims> userInfo = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        final Map<String, Object> result = new HashMap<String, Object>();
        result.put("userNum", userInfo.getBody().getSubject());
        result.put("authorities", userInfo.getBody().get("roles", List.class));
        return result;
    }

	/**
	 * Http Request의 Authorization header에 담긴 토큰(암호화된 사용자의 토큰)을 가져오는 메소드입니다. 
	 * X-AUTH-TOKEN라는 이름으로 파싱합니다.
	 * @param req {@link HttpServletRequest}
	 * @return 암호화된 사용자의 문자열
	 */
	public String resolveToken(final HttpServletRequest req) {
		return req.getHeader("X-AUTH-TOKEN");
	}

	/**
	 * 토큰의 유효성을 검사하는 메소드 입니다.
	 * @param jwtToken
	 * @return
	 */
	public boolean validateToken(final String jwtToken) {
		try {
			final Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (final Exception e) {
			return false;
		}
	}
}

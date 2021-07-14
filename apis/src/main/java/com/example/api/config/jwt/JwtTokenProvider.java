package com.example.api.config.jwt;


import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;


import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
//토큰의 생성, 토큰의 유효성을 검증 담당
public class JwtTokenProvider {
	
	//1. Secretkey 설정
	private String secretKey = "SECURITY-JWT_TOKEN-TUTORIAL";
	
	//2. 토큰 유효시간 설정
	private long tokenValidMilisecond = 1000L * 60 * 60; // 1시간만 토큰 유지한다.
	
	private  UserDetailsService userDetailsService;
	
	@PostConstruct
	protected void init() {
		secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
	}
	
	//4. JWT 토큰 생성
	public String createToken(String userPk, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(userPk);
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
				.setClaims(claims) //데이터
				.setIssuedAt(now) //토큰 발행일자
				.setExpiration(new Date(now.getTime() + tokenValidMilisecond)) //토큰 종료시간
				.signWith(SignatureAlgorithm.HS256, secretKey) //암호화 알고리즘 방식
				.compact();
	}
	
	//5. Jwt 토큰으로 인증정보를 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "" ,userDetails.getAuthorities());
	}
	
	//6. JWT 토큰에서 회원 구별 정보 추출
	public String getUserPk(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	//7. Request의 Header에서 token 파싱 : "X-AUTH-TOKEN : jwt 토큰"
	public String resolveToken(HttpServletRequest req) {
		return req.getHeader("X-AUTH-TOKEN");
	}
	
	// Jwt 토큰의 유효성 + 만료일자 확인
	public boolean vaildateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}
}
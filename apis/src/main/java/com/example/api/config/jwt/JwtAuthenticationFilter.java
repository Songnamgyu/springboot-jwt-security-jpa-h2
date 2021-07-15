package com.example.api.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;


public class JwtAuthenticationFilter extends GenericFilterBean {

	private  JwtTokenProvider jwtTokenProvider;
	
	 // Jwt Provier 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

	//Request로 들어오는 Jwt Token의 유효성을 검증(JwtTokenProvider.validateToken)하는 filter를 filterChain에 등록합니다.
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		//1. 헤더에서 JWT를 받아옵니다.
		String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
		
		//2. 유효한 토큰인지 확인합니다.
		if(token != null && jwtTokenProvider.validateToken(token)) {
			
			//3. 토큰이 유효하면 토큰으로부터 유저정보를 받아옵니다.
			Authentication auth = jwtTokenProvider.getAuthentication(token);
			
			//4.SecurityContext에 Authentication 객체를 저장합니다.
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		chain.doFilter(request, response);
	}

}

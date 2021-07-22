package com.example.api.config.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.api.config.jwt.JwtAuthenticationFilter;
import com.example.api.config.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	
	private final JwtTokenProvider jwtTokenProvider;
	
	// authenticationManager를 Bean으로 등록합니다.
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			.httpBasic().disable() //rest api만을 고려하여 기본설정은 해제합니다.
			.csrf().disable() // csrf 보안토큰 disable처리 
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 기반 인증이므로 세션 역시 사용하지 않습니다.
			.and()
				.authorizeRequests() //요청에 대한 사용권한 체크
				.antMatchers("/v1/signin", "/v1/signup","/api/signUp").permitAll()
				.antMatchers(HttpMethod.GET, "/excpetion/**").permitAll()
				.antMatchers("/v1/admin/**").hasRole("ADMIN")
				.antMatchers("/v1/users","/v1/user","/v1/user/**").hasAnyRole("USER", "ADMIN")
				.anyRequest().authenticated()
			.and()
				//JWT token 필터를 id/password 인증 필터 전에 넣어야됨
				.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
	}
	
	//비밀번호 암호화 객체 선언
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/h2-console/**", "/v2/api-docs", "/swagger-resources/**",
                					"/swagger-ui.html", "/webjars/**", "/swagger/**");
		
		web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());

	}
}

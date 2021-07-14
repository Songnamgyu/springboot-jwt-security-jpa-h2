package com.example.api.model.contorller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.config.jwt.JwtTokenProvider;
import com.example.api.model.dao.UserJpaRepo;
import com.example.api.model.dto.entity.User;
import com.example.api.model.response.CommonResult;
import com.example.api.model.response.SingleResult;
import com.example.api.model.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = {"1. Sign"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value="/v1")
public class SignController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final JwtTokenProvider jwtTokenprovider;
	private final ResponseService responseService;
	private final PasswordEncoder passwordEncoder;
	private final UserJpaRepo userJpaRepo;
	
	
	// 1. 회원가입
	@ApiOperation(value = "가입", notes = "회원가입을 한다.")
	@PostMapping(value = "/signup")
	public CommonResult signIn(@ApiParam (value = "회원 ID : USER_ID", required = true ) @RequestParam String id ,
								@ApiParam (value = "비밀번호 : " , required = true) @RequestParam String password ,
								@ApiParam (value = "이름 : " , required = true) @RequestParam String name) {
		
		userJpaRepo.save(User.builder()
					.userId(id)
					.password(passwordEncoder.encode(password))
					.userName(name)
					.roles(Collections.singletonList("ROLE_USER"))
					.build());
		
		return responseService.getSuccessResult();
	}
	
	
	
	
	// 2. 로그인 
	@ApiOperation(value = "로그인" , notes = "USER_ID로 회원 로그인을 한다.")
	@PostMapping(value ="/signin")
	public SingleResult<String> signIn(@ApiParam(value = "회원 ID : USER_ID", required = true) @RequestParam String id,
										@ApiParam(value = "비밀번호", required = true) @RequestParam String password) throws Exception{
		
		User user = userJpaRepo.findByUserId(id);
		if (!passwordEncoder.matches(password, user.getPassword())) throw new Exception();
		
		return responseService.getSingleResult(jwtTokenprovider.createToken(String.valueOf(user.getId()), user.getRoles()));
		
		
	}
	
}

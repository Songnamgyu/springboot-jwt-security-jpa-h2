package com.example.api.model.contorller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.dao.UserJpaRepo;
import com.example.api.model.dto.entity.User;
import com.example.api.model.response.ListResult;
import com.example.api.model.response.SingleResult;
import com.example.api.model.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.This;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(This.class);

	private final UserJpaRepo userJpaRepo;
	private final ResponseService responsService;
	
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")		
	})
	@ApiOperation(value = "회원리스트 조회" , notes = "모든 회원을 조회한다.")
	@GetMapping(value="/users")
	public ListResult<User> findAllUser(){
		// 결과 데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력한다.
		return responsService.getListResult(userJpaRepo.findAll());
	}

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 단건 조회", notes = "회원번호(Id)로 회원을 조회한다.")
	@GetMapping(value = "/user")
	public SingleResult<User> findUserById(@ApiParam(value = "언어", defaultValue = "ko" ) @RequestParam String lang) {
		
		//SecurityContext에서 인증받은 회원의 정보를 얻어온다.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		logger.info("authentication : " + authentication);
		String id = authentication.getName();
		logger.info("id  :  " + id);
		
		//결과데이터가 단일건인 경우 getSingleResult를 이용해서 결과를 출력한다.
		return responsService.getSingleResult(userJpaRepo.findByUserId(id));
	}
}


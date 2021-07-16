package com.example.api.model.contorller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.dao.UserJpaRepo;
import com.example.api.model.dto.entity.User;
import com.example.api.model.response.CommonResult;
import com.example.api.model.response.ListResult;
import com.example.api.model.response.SingleResult;
import com.example.api.model.service.ResponseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.This;

@Api(tags = {"2. User"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
@ApiResponses({
    @ApiResponse(code = 200, message = "OK 성공 !!"),
    @ApiResponse(code = 500, message = "서버 에러 ! Internal Server Error !!"),
    @ApiResponse(code = 404, message = "페이지를 찾을수 없어요! Not Found !!"),
    @ApiResponse(code = 403, message = "접근 거부 ! Access Denied !!")
})
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(This.class);

	private final UserJpaRepo userJpaRepo;
	private final ResponseService responsService;
	private final PasswordEncoder passwordEncoder;

	
	// 1. 전체 회원 조회
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")		
	})
	@ApiOperation(value = "회원리스트 조회" , notes = "모든 회원을 조회한다.")
	@GetMapping(value="/admin/selectAll")
	public ListResult<User> findAllUser(){
		
		// 결과 데이터가 여러건인 경우 getListResult를 이용해서 결과를 출력한다.
		return responsService.getListResult(userJpaRepo.findAll());
	}

	// 2. ID를 이용한 회원조회
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "해당 회원의 정보 조회", notes = "회원번호(Id)로 회원을 조회한다.")
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


	// 3. 회원 수정
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN" , value = "로그인 성공 후 access_Token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 수정", notes = "회원정보를 수정한다.")
	@PutMapping(value="/user")
	public SingleResult<User> modify (@ApiParam(value = "회원번호", required = true) @RequestParam Long pk,
			@ApiParam(value = "회원이름", required = true) @RequestParam String name	,
			@ApiParam(value = "비밀번호", required = true) @RequestParam String password,
			@ApiParam(value = "회원아이디", required = true) @RequestParam String id) {
		
		User user = User.builder()
				.id(pk)
				.password(passwordEncoder.encode(password))
				.userId(id)
				.userName(name)
				.roles(Collections.singletonList("ROLE_USER"))
				.build();

		return responsService.getSingleResult(userJpaRepo.save(user));
	}

	// 4. 회원 삭제
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token" , required = true, dataType = "String", paramType = "header") 
	})
	@ApiOperation(value= "회원삭제" , notes = "회원정보를 삭제한다")
	@DeleteMapping(value = "/admin/delete/{id}")
	public CommonResult delete (@ApiParam(value = "회원번호" , required = true) @PathVariable Long id) {

		userJpaRepo.deleteById(id);

		// 성공결과 정보만 필요한 경우 getSuccessResult()를 이용하여 결과를 출력한다.
		return responsService.getSuccessResult();

	}

	// 5. 회원 권한 변경 (admin권한만)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "로그인 성공 후 access_token", required = true, dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "회원 권한 변경" , notes = "회원의 권한을 변경한다.")
	@PutMapping("/admin")
	public SingleResult<User> modifyRole(@ApiParam(value = "회원번호", required = true) @RequestParam Long pk,
										 @ApiParam(value = "회원이름", required = true) @RequestParam String name	,
										 @ApiParam(value = "비밀번호", required = true) @RequestParam String password,
										 @ApiParam(value = "회원아이디", required = true) @RequestParam String id,
										 @ApiParam(value = "회원등급" , required = true) @RequestParam String role){
		
		User user = User.builder()
				.id(pk)
				.password(passwordEncoder.encode(password))
				.userId(id)
				.userName(name)
				.roles(Collections.singletonList("ROLE_" + role))
				.build();
		
		return responsService.getSingleResult(userJpaRepo.save(user));
	}

}


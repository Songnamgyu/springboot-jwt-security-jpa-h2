package com.example.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CommonResult {

	/*
	 * @ApioModelProperty란  ?
	 * @ApiModelProperty 모델 내의 필드를 설명할 때 사용한다.
	 * Swagger 어노테이션이다
	 * VO, DTO, Entiry등 모델에서 사용하는 어노테이션
	 * required : 필수 여부를 적는 옵션
	 * value : 필드의 이름을 적는 옵션
	 * example : 필드의 예시를 적는 옵션
	 * hidden : 필드를 숨기는 여부를 적는 옵션
	 * */
	@ApiModelProperty(value = "응답 성공여부 : true/false")
	private boolean success;

	@ApiModelProperty(value = "응답 코드 번호 : > 0 정상, < 0 비정상")
	private int code;

	@ApiModelProperty(value = "응답 메시지")
	private String msg;

}

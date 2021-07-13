package com.example.api.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//UserRequestDTO는 정보를 얻기위한 제공되어야하는 최소한의 요소를 표현한거다.
public class UserRequestDTO {

	private String userId;
	private String password;
}

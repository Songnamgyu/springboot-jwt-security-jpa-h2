package com.example.api.model.dto;

import com.example.api.enums.Roles;
import com.example.api.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
//UserResponseDTO는 UserRequestDTO가 제공해주는 정보가 맞으면 그에 해당하는 내용의 범위를 정해주는 DTO라고 생각하면 된다.
public class UserResponseDTO {
	
	private String userId;
	private String userName;
	private Status status;
	private Roles role;
	
	@Builder
	public UserResponseDTO(String userId, String userName, String password, Roles role) {
		this.userId = userId;
		this.userName = userName;
		this.role = role;
		this.status = Status.NONBLOCKED;
	}

}

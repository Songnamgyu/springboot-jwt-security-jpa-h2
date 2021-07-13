package com.example.api.model.dto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.example.api.enums.Roles;
import com.example.api.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false)
	private String userName;
	
	@Column(nullable = false)
	private String password;		
	
	private Status status;
	
	private Roles role;
	
	@Builder
	public User(String userId, String userName, String password, Roles role) {
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.status = Status.NONBLOCKED;
	}
}

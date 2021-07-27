package com.example.api.model.dto.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User implements UserDetails {

	private static final long serialVersionUID = 1957380853771500260L;
	
	@PrePersist
	public void prePersist() {
		System.out.println(">>>>>> prePersist");
		System.out.println(this.createdAt = LocalDateTime.now());
		System.out.println(this.updateAt = LocalDateTime.now());

	}
	
	
	@PostPersist
	public void postPersist() {
		System.out.println(">>>>>> postPersist");
		System.out.println(this.createdAt = LocalDateTime.now());
		System.out.println(this.updateAt = LocalDateTime.now());
	}

	@PostLoad
	public void postLoad() {
		System.out.println("=================== PostLoad ==================");
	}
	
	@Id
	//@GenerateValue는 SQL에서으 sequence개념과 비슷하다
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String userId;

	@Column(nullable = false)
	private String password;		

	@Column(nullable = false)
	private String userName;

	
	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	private LocalDateTime createdAt;

	private LocalDateTime updateAt;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		return this.roles.stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
	}

	@Override
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	public String getUsername() {

		return this.userId;
	}
	
	@Override
	public String getPassword() {

		return this.password;
	}
	

	public String getUserName() {
		return this.userName;
	}
	

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isAccountNonLocked() {

		return true;
	}
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Override
	public boolean isEnabled() {

		return true;
	}

}

package com.example.api.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.dto.entity.User;

public interface UserJpaRepo extends JpaRepository<User, Long> {

	User findByUserId(String userId);

	


}

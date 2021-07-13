package com.example.api.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.example.api.config.security.UserPrincipal;
import com.example.api.model.dao.UserJpaRepo;
import com.example.api.model.dto.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;
    @Override
    public UserDetails loadUserByUsername(String userId) {
    
    	//UserId와 일치하는 사용자를찾음
    	User user = this.userJpaRepo.findByUserId(userId);
    	
    	//db에서 찾은 사용자를 사용해 UserPrincipal객체 생성
    	UserPrincipal userPrincipal = new UserPrincipal(user);
    	
    	return userPrincipal;
    }
}

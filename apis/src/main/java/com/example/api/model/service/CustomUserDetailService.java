package com.example.api.model.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.api.exception.CUserNotFoundException;
import com.example.api.model.dao.UserJpaRepo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

	@Override
	public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
		
		return userJpaRepo.findById(Long.valueOf(userPk)).orElseThrow(CUserNotFoundException::new);}

	}
  
    


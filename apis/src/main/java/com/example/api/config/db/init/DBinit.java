package com.example.api.config.db.init;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.model.dao.UserJpaRepo;
import com.example.api.model.dto.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DBinit implements CommandLineRunner {
	private final UserJpaRepo userRepository;
	private final PasswordEncoder passwordEncoder;
	
	/**
	 * 매번 회원가입 하지 않고 어플리케이션 실행 되면 사용자 정보를 미리 넣음(테스트 용도)
	 */
	@Override
	public void run(String... args) throws Exception {
		// initialize
		this.userRepository.deleteAll();

		List<String> auth = Arrays.asList("ROLE_ADMIN");
		// user, admin+password encode
		User admin = new User(0L, "Admin", passwordEncoder.encode("1234"), "운영자", auth);

		List<User> users = Arrays.asList(admin);
		this.userRepository.saveAll(users);
	}
}

package com.example.api.config.db.init;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.api.model.board.dto.entity.Board;
import com.example.api.model.board.repository.BoardRepository;
import com.example.api.model.dao.UserJpaRepo;
import com.example.api.model.dto.entity.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DBinit implements CommandLineRunner {
	
	private final UserJpaRepo userJpaRepo;
	private final PasswordEncoder passwordEncoder;
	private final BoardRepository boardRepositry;
	
	/*
	 * 매번 회원가입 하지 않고 어플리케이션 실행 되면 관리자 정보를 미리 넣음(테스트 용도)
	 */
	@Override
	public void run(String... args) throws Exception {
		// initialize
		// 유저정보
		this.userJpaRepo.deleteAll();
		// 게시글 정보
		this.boardRepositry.deleteAll();

		// 관리자의 권한을 ADMIN으로 지정해준다.
		List<String> auth = Arrays.asList("ROLE_ADMIN");
		
		// 관리자에 해당하는 정보(Entity)에 맞게 넣어준다.
		User admin = new User(0L, "Admin", passwordEncoder.encode("1234"), "운영자", auth);
		
		// 게시판에 해당되는 정보 (Entity)에  맞게 넣어줌
		Board origin1 = new Board(1L, "첫번째 게시글", "ADMIN", "첫번째 게시글내용 테스트입니다.");
		Board origin2 = new Board(2L, "두번째 게시글", "USER", "두번째 게시글내용 테스트입니다.");
		Board origin3= new Board(3L, "세번째 게시글", "GUEST", "세번째 게시글내용 테스트입니다.");
		
		// 위에서 선언한 admin에 정보를 List<User>타입에 Admin에 담는다.
		List<User> Admin = Arrays.asList(admin);
		
		List<Board> board1 = Arrays.asList(origin1);
		List<Board> board2 = Arrays.asList(origin2);
		List<Board> board3 = Arrays.asList(origin3);
		
		
		// 담은 정보를 userJpaRepo에 저장시킨다
		this.userJpaRepo.saveAll(Admin);
		this.boardRepositry.saveAll(board1);
		this.boardRepositry.saveAll(board2);
		this.boardRepositry.saveAll(board3);
	}
}

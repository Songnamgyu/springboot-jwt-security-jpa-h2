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
		Board origin4= new Board(4L, "네번째 게시글", "GUEST", "네번째 게시글내용 테스트입니다.");
		Board origin5= new Board(5L, "다섯번째 게시글", "GUEST", "다섯번째 게시글내용 테스트입니다.");
		Board origin6= new Board(6L, "여섯번째 게시글", "GUEST", "여섯번째 게시글내용 테스트입니다.");
		Board origin7= new Board(7L, "일곱번째 게시글", "GUEST", "일곱번째 게시글내용 테스트입니다.");
		Board origin8= new Board(8L, "여덟번째 게시글", "GUEST", "여덟번째 게시글내용 테스트입니다.");
		Board origin9= new Board(9L, "아홉번째 게시글", "GUEST", "아홉번째 게시글내용 테스트입니다.");
		Board origin10= new Board(10L, "열번째 게시글", "GUEST", "열번째 게시글내용 테스트입니다.");
		Board origin11= new Board(11L, "열한번째 게시글", "GUEST", "열한번째 게시글내용 테스트입니다.");
		Board origin12= new Board(12L, "열두번째 게시글", "GUEST", "열두번째 게시글내용 테스트입니다.");
		Board origin13= new Board(13L, "열세번째 게시글", "GUEST", "열세번째 게시글내용 테스트입니다.");
		Board origin14= new Board(14L, "열네번째 게시글", "GUEST", "열네번째 게시글내용 테스트입니다.");
		Board origin15= new Board(15L, "열다섯번째 게시글", "GUEST", "열다섯번째 게시글내용 테스트입니다.");
		Board origin16= new Board(16L, "열여섯번째 게시글", "GUEST", "열여섯번째 게시글내용 테스트입니다.");
		Board origin17= new Board(17L, "열일곱번째 게시글", "GUEST", "열일곱번째 게시글내용 테스트입니다.");
		Board origin18= new Board(18L, "열여덟번째 게시글", "GUEST", "열여덟번째 게시글내용 테스트입니다.");
		Board origin19= new Board(19L, "열아홉번째 게시글", "GUEST", "열아홉번째 게시글내용 테스트입니다.");
		Board origin20= new Board(20L, "스물번째 게시글", "GUEST", "스물번째 게시글내용 테스트입니다.");
		Board origin21= new Board(21L, "스물한번째 게시글", "GUEST", "스물한번째 게시글내용 테스트입니다.");
		Board origin22= new Board(22L, "스물두번째 게시글", "GUEST", "스물두번째 게시글내용 테스트입니다.");
		Board origin23= new Board(23L, "스뭃세번째 게시글", "GUEST", "스뭃세번째 게시글내용 테스트입니다.");
		Board origin24= new Board(24L, "스물네번째 게시글", "GUEST", "스물네번째 게시글내용 테스트입니다.");
		Board origin25= new Board(25L, "스물다섯번째 게시글", "GUEST", "스물다섯번째 게시글내용 테스트입니다.");
		Board origin26= new Board(26L, "스물여섯번째 게시글", "GUEST", "스물여섯번째 게시글내용 테스트입니다.");
		Board origin27= new Board(27L, "스물일곱번째 게시글", "GUEST", "스물일곱번째 게시글내용 테스트입니다.");
		Board origin28= new Board(28L, "스물여덟번째 게시글", "GUEST", "스물여덟번째 게시글내용 테스트입니다.");
		
		
		// 위에서 선언한 admin에 정보를 List<User>타입에 Admin에 담는다.
		List<User> Admin = Arrays.asList(admin);
		
		List<Board> board1 = Arrays.asList(origin1);
		List<Board> board2 = Arrays.asList(origin2);
		List<Board> board3 = Arrays.asList(origin3);
		List<Board> board4 = Arrays.asList(origin4);
		List<Board> board5 = Arrays.asList(origin5);
		List<Board> board6 = Arrays.asList(origin6);
		List<Board> board7 = Arrays.asList(origin7);
		List<Board> board8 = Arrays.asList(origin9);
		List<Board> board9 = Arrays.asList(origin9);
		List<Board> board10 = Arrays.asList(origin10);
		List<Board> board11 = Arrays.asList(origin11);
		List<Board> board12 = Arrays.asList(origin12);
		List<Board> board13 = Arrays.asList(origin13);
		List<Board> board14 = Arrays.asList(origin14);
		List<Board> board15 = Arrays.asList(origin15);
		List<Board> board16 = Arrays.asList(origin16);
		List<Board> board17 = Arrays.asList(origin17);
		List<Board> board18 = Arrays.asList(origin18);
		List<Board> board19 = Arrays.asList(origin19);
		List<Board> board20 = Arrays.asList(origin20);
		List<Board> board21 = Arrays.asList(origin21);
		List<Board> board22 = Arrays.asList(origin22);
		List<Board> board23 = Arrays.asList(origin23);
		List<Board> board24 = Arrays.asList(origin24);
		List<Board> board25 = Arrays.asList(origin25);
		List<Board> board26 = Arrays.asList(origin26);
		List<Board> board27 = Arrays.asList(origin27);
		List<Board> board28 = Arrays.asList(origin28);
		
		
		// 담은 정보를 userJpaRepo에 저장시킨다
		this.userJpaRepo.saveAll(Admin);
		this.boardRepositry.saveAll(board1);
		this.boardRepositry.saveAll(board2);
		this.boardRepositry.saveAll(board3);
		this.boardRepositry.saveAll(board4);
		this.boardRepositry.saveAll(board5);
		this.boardRepositry.saveAll(board6);
		this.boardRepositry.saveAll(board7);
		this.boardRepositry.saveAll(board8);
		this.boardRepositry.saveAll(board9);
		this.boardRepositry.saveAll(board10);
		this.boardRepositry.saveAll(board11);
		this.boardRepositry.saveAll(board12);
		this.boardRepositry.saveAll(board13);
		this.boardRepositry.saveAll(board14);
		this.boardRepositry.saveAll(board15);
		this.boardRepositry.saveAll(board16);
		this.boardRepositry.saveAll(board17);
		this.boardRepositry.saveAll(board18);
		this.boardRepositry.saveAll(board19);
		this.boardRepositry.saveAll(board20);
		this.boardRepositry.saveAll(board21);
		this.boardRepositry.saveAll(board22);
		this.boardRepositry.saveAll(board23);
		this.boardRepositry.saveAll(board24);
		this.boardRepositry.saveAll(board25);
		this.boardRepositry.saveAll(board26);
		this.boardRepositry.saveAll(board27);
		this.boardRepositry.saveAll(board28);
	}
}

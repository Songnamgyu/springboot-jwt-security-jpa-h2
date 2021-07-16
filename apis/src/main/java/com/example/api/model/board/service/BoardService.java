package com.example.api.model.board.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.api.model.board.dto.BoardResponseDTO;
import com.example.api.model.board.dto.BoardUpdateRequestDTO;
import com.example.api.model.board.dto.entity.Board;
import com.example.api.model.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository;

	/* 1. 게시글 - 목록 조회 */ 
	public List<BoardResponseDTO> findAll()
	{
		// map으로 BoardResponseDTO에 새로운 객체를 생성한 뒤 Colletors.toList를 이용하여 List계열로 결과를가지고온다.
		return boardRepository.findAll().stream().map(BoardResponseDTO::new).collect(Collectors.toList());
	}


	/* 2. 게시글 - 상세 조회 */
	public BoardResponseDTO findById(Long seq) {

		Board board = boardRepository.findById(seq).orElseThrow(() -> new IllegalAccessError("[seq= " + seq + "] 해당 게시글이 존재하지 않습니다."));

		return new BoardResponseDTO(board);
	}

	/* 3. 게시글 - 등록 */
	public BoardResponseDTO registBoard(String subject, String writer, String content) {

		return new BoardResponseDTO(boardRepository.save(Board.builder()
				.subject(subject)
				.writer(writer)
				.content(content)
				.build()));
	}

	/* 4. 게시글 - 수정*/
	public Long update(Long seq, BoardUpdateRequestDTO board) {

		Board updateBoard = boardRepository.findById(seq).orElseThrow( () -> new IllegalAccessError("[seq= " + seq + "] 해당 게시글이 존재하지 않습니다."));
		updateBoard.update( board.getSubject(), board.getContent());
		return seq;
	}




}

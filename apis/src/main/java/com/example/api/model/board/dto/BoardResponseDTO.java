package com.example.api.model.board.dto;

import com.example.api.model.board.dto.entity.Board;

import lombok.Getter;

@Getter

public class BoardResponseDTO {

	private Long seq;
	private String subject;
	private String writer;
	private String content;
	
	public BoardResponseDTO(Board board) {
		this.seq = board.getSeq();
		this.subject = board.getSubject();
		this.writer = board.getWriter();
		this.content = board.getContent();
	}
}

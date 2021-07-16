package com.example.api.model.board.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdateRequestDTO {

	private String subject;
	private String content;
	
	@Builder
	public BoardUpdateRequestDTO(String subject, String content) {
		this.subject = subject;
		this.content = content;
	}
}

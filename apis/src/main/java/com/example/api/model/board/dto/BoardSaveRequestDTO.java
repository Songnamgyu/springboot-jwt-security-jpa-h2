package com.example.api.model.board.dto;

import com.example.api.model.board.dto.entity.Board;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDTO {

	//Entity 클래스를 변경하는 경우 DB Layer에 영향을 주기 때문에, ViewLayer에서 사용할 Request/ResponseDTO를 추가해주자
	
	private String subject;
	private String writer;
	private String content;
	
	
	// 필요한 값들만 모아서 @Builder패턴 생성
	@Builder
	public BoardSaveRequestDTO(String subject, String writer, String content) {
		this.subject = subject;
		this.writer = writer;
		this.content = content;
	}
	
	// 해당 값들을 Board에다가 변경시켜주는거?
	public Board toEntity() {
		return Board.builder()
				    .subject(subject)
				    .writer(writer)
				    .content(content)
				    .build();
	}
}

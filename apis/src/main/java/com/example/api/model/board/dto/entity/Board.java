package com.example.api.model.board.dto.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity //Board 테이블을 이 클래스를 토대로 만들어라 라는 말
@AllArgsConstructor
public class Board {

	
	@Id //PK
	@GeneratedValue(strategy = GenerationType.IDENTITY) //추가될수록 @Id의 값이 1씩 증가
	private Long seq;
	
	//게시판 제목
	@Column(nullable = false)
	private String subject;

	//게시판 작성자
	@Column(nullable = false)
	private String writer;
	
	//게시판 내용
	@Column(nullable = false)
	private String content;
	
	@Builder
	public Board(String subject, String writer, String content) {
		this.subject = subject;
		this.writer = writer;
		this.content = content;
	}
	
	//게시판 업데이트(수정시)
	public void update(String subject , String content) {
		this.subject = subject;
		this.content = content;
	}
}

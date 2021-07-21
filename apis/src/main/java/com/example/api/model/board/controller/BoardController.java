package com.example.api.model.board.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.model.board.dto.BoardResponseDTO;
import com.example.api.model.board.dto.BoardUpdateRequestDTO;
import com.example.api.model.board.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice.This;

@Api(tags = {"3. Board"})
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1")
@ApiResponses({
    @ApiResponse(code = 200, message = "OK 성공 !!"),
    @ApiResponse(code = 500, message = "서버 에러 ! Internal Server Error !!"),
    @ApiResponse(code = 404, message = "페이지를 찾을수 없어요! Not Found !!"),
    @ApiResponse(code = 403, message = "접근 거부 ! Access Denied !!")
})
public class BoardController {
	
	private static final Logger logger = LoggerFactory.getLogger(This.class);

	private final BoardService boardService;

	// 1. 게시글 - 목록 조회
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Please Input AccessToken", required = true, dataType = "String", paramType = "header")		
	})
	@ApiOperation(value = "게시글 조회" , notes = "게시글을 조회한다.")
	@GetMapping(value = "/user/selectAllBoard")
	public ResponseEntity<List<BoardResponseDTO>> findAll(final Pageable pageable) {
		
		List<BoardResponseDTO> boardSelectAllList = boardService.findAll(pageable);
		
		return new ResponseEntity<List<BoardResponseDTO>>(boardSelectAllList, HttpStatus.OK);
	}
	
	// 2. 게시글 - 상세 조회
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Please Input AccessToken", required = true, dataType = "String", paramType = "header")		
	})
	@ApiOperation(value = "게시글 상세조회" , notes = "해당게시글을 상세 조회한다.")
	@PostMapping(value = "/user/board/{seq}")
	public ResponseEntity<BoardResponseDTO> findById(@RequestParam("seq") Long seq){
		
		BoardResponseDTO boardResponseDTO = boardService.findById(seq);
		
		return new ResponseEntity<BoardResponseDTO>(boardResponseDTO, HttpStatus.OK);
	}
	
	
	// 3. 게시글 등록
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Please Input AccessToken", required = true, dataType = "String", paramType = "header")		
	})
	@ApiOperation(value = "게시글 등록" , notes = "게시글을 등록합니다.")
	@PostMapping(value = "/user/board/regist")
	public ResponseEntity<BoardResponseDTO> regist (@ApiParam (value = "제목 : ", required = true ) @RequestParam String subject ,
			@ApiParam (value = "글쓴이 : " , required = true) @RequestParam String writer ,
			@ApiParam (value = "내용: " , required = true) @RequestParam String content) {
		
		
			BoardResponseDTO registBoard = boardService.registBoard(subject, writer,content);
			
			return new ResponseEntity<BoardResponseDTO>(registBoard, HttpStatus.OK);
	}
	
	// 4. 게시글 수정
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Please Input AccessToken", required = true, dataType = "String", paramType = "header")		
	})
	@ApiOperation(value = "게시글 수정" , notes = "게시글을 수정합니다.")
	@PutMapping(value = "/user/board/update/{seq}")
	public ResponseEntity<Long> updateBoard (@PathVariable("seq") Long seq,
			@ApiParam (value = "제목 : ", required = true ) @RequestParam String subject,	
			@ApiParam (value = "내용: " , required = true) @RequestParam String content) {
			
			BoardUpdateRequestDTO board = new BoardUpdateRequestDTO(subject,content);
		
			Long updateBoardSeq = boardService.update(seq, board);
			logger.info("board : " + board.toString());
			logger.info("updateBoardSeq : " + updateBoardSeq.toString());
			
			return new ResponseEntity<Long>(updateBoardSeq, HttpStatus.CREATED);
	}
	
	// 5. 게시글 삭제
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", value = "Please Input AccessToken", required = true , dataType = "String", paramType = "header")
	})
	@ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제 합니다.")
	@DeleteMapping(value = "/user/board/delete/{seq}",  produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Long> deleteBoard (@PathVariable("seq") Long seq){
		
		boardService.deleteBoard(seq);
		
		return new ResponseEntity<Long>(seq, HttpStatus.NO_CONTENT);
	}
}

package com.example.api.model.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.board.dto.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long>{

}

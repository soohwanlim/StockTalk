package com.mycom.myapp.board.service;

import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

// Dao Layer 가 여러 개의 메소드로 분리되어도 하나의 B.L 처리하는 Service Layer 는 1개의 메소드로 처리
public interface BoardService {
	BoardResultDto listBoard(BoardParamDto boardParamDto); // limit, offset
}

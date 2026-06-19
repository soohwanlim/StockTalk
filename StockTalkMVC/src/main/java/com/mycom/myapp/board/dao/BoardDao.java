package com.mycom.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;

@Mapper
public interface BoardDao {

	// 목록, 목록별 전체 건수
	List<BoardDto> listBoard(BoardParamDto boardParamDto);
	int listBoardTotalCount();
}

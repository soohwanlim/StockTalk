package com.mycom.myapp.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mycom.myapp.board.dao.BoardDao;
import com.mycom.myapp.board.dto.BoardDto;
import com.mycom.myapp.board.dto.BoardParamDto;
import com.mycom.myapp.board.dto.BoardResultDto;

@Service
public class BoardServiceImpl implements BoardService{

	// 생성자 DI
	private final BoardDao boardDao;
	
	public BoardServiceImpl(BoardDao boardDao) {
		this.boardDao = boardDao;
	}
	
	// 비정상적인 실패 상황은 ?????
	@Override
	public BoardResultDto listBoard(BoardParamDto boardParamDto) {
		BoardResultDto boardResultDto = new BoardResultDto();
		
		List<BoardDto> list = boardDao.listBoard(boardParamDto); // 목록
		int count = boardDao.listBoardTotalCount(); // 전체 건수
		boardResultDto.setList(list);
		boardResultDto.setCount(count);
		boardResultDto.setResult("success");
		
		return boardResultDto;
	}

}

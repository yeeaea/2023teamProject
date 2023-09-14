package com.pet.free.dto;

import java.time.LocalDateTime;

import com.pet.free.domain.FreeBoard;

import lombok.Getter;

@Getter
public class FreeBoardListViewResponse {

	private Long freeNo;
	private String freeTitle;
	private String freeContent;
	private LocalDateTime freeRdate;
	private LocalDateTime freeUdate;
	
	public FreeBoardListViewResponse(FreeBoard freeBoard) {
		this.freeNo = freeBoard.getFreeNo();
		this.freeTitle = freeBoard.getFreeTitle();
		this.freeContent = freeBoard.getFreeContent();
		this.freeRdate = freeBoard.getFreeRdate();
		this.freeUdate = freeBoard.getFreeUdate();
	}
}

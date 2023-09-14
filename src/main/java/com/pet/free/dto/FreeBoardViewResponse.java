package com.pet.free.dto;

import java.time.LocalDateTime;

import com.pet.free.domain.FreeBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FreeBoardViewResponse {
	private Long freeNo;
	private String freeTitle;
	private String freeContent;
	private LocalDateTime freeRdate;
	private String freeFilename;
	private String freeFilepath;

	public FreeBoardViewResponse(FreeBoard freeBoard) {
		this.freeNo = freeBoard.getFreeNo();
		this.freeTitle = freeBoard.getFreeTitle();
		this.freeContent = freeBoard.getFreeContent();
		this.freeRdate = freeBoard.getFreeRdate();
		this.freeFilename = freeBoard.getFreeFilename();
		this.freeFilepath = freeBoard.getFreeFilepath();
	}
}

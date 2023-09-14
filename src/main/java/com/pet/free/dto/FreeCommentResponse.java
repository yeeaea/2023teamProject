package com.pet.free.dto;

import java.time.LocalDateTime;

import com.pet.free.domain.FreeComment;

import lombok.Getter;

@Getter
public class FreeCommentResponse {
	private String freeCmtContent;
	private LocalDateTime freeCmtRdate;
	private LocalDateTime freeCmtUdate;
	
	public FreeCommentResponse(FreeComment freeComment) {
		this.freeCmtContent = freeComment.getFreeCmtContent();
		this.freeCmtRdate = freeComment.getFreeCmtRdate();
		this.freeCmtUdate = freeComment.getFreeCmtUdate();
	}
}

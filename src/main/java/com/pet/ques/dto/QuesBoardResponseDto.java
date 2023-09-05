package com.pet.ques.dto;

import java.time.LocalDateTime;

import com.pet.ques.domain.QuesBoard;

import lombok.Getter;

@Getter
// 응답 dto
public class QuesBoardResponseDto {
	
	private String quesTitle;
	private String quesContent;
	private LocalDateTime quesRdate;
	private LocalDateTime quesUdate;
//	private Integer quesViews;
//	private String memId;
	
	public QuesBoardResponseDto(QuesBoard quesBoard) {
		this.quesTitle = quesBoard.getQuesTitle();
		this.quesContent = quesBoard.getQuesContent();
		this.quesRdate = quesBoard.getQuesRdate();
		this.quesUdate = quesBoard.getQuesUdate();
//		this.quesViews = quesBoard.getQuesViews();
//		this.memId = quesBoard.getMemId();
	}
}

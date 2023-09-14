package com.pet.ques.dto;

import java.time.LocalDateTime;

import com.pet.ques.domain.QuesBoard;

import lombok.Getter;

@Getter
public class QuesBoardResponse {
	
	private String quesTitle;
	private String quesContent;
	private LocalDateTime quesRdate;
	private LocalDateTime quesUdate;
	
	public QuesBoardResponse(QuesBoard quesBoard) {
		this.quesTitle = quesBoard.getQuesTitle();
		this.quesContent = quesBoard.getQuesContent();
		this.quesRdate = quesBoard.getQuesRdate();
		this.quesUdate = quesBoard.getQuesUdate();
	}
}

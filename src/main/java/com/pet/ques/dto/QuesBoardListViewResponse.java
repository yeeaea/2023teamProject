package com.pet.ques.dto;

import java.time.LocalDateTime;
import java.util.Date;

import com.pet.ques.domain.QuesBoard;

import lombok.Getter;

@Getter
public class QuesBoardListViewResponse {

	private final Long quesNo;
	private final String quesTitle;
	private final String quesContent;
	private final LocalDateTime quesRdate;
	private final LocalDateTime quesUdate;
//	private final Integer quesViews; 
//	private final String memId;
	
	public QuesBoardListViewResponse(QuesBoard quesBoard) {
		this.quesNo = quesBoard.getQuesNo();
		this.quesTitle = quesBoard.getQuesTitle();
		this.quesContent = quesBoard.getQuesContent();
		this.quesRdate = quesBoard.getQuesRdate();
		this.quesUdate = quesBoard.getQuesUdate();
//		this.quesViews = quesBoard.getQuesViews();
//		this.memId = quesBoard.getMemId();
	}
}

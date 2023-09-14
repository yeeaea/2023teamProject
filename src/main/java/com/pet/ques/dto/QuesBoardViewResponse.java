package com.pet.ques.dto;

import java.time.LocalDateTime;

import com.pet.ques.domain.QuesBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class QuesBoardViewResponse {
	
	private Long quesNo;
	private String quesTitle;
	private String quesContent;
	private LocalDateTime quesRdate;
	private String quesFilename;
	private String quesFilepath;
	
	public QuesBoardViewResponse(QuesBoard quesBoard) {
		this.quesNo = quesBoard.getQuesNo();
		this.quesTitle = quesBoard.getQuesTitle();
		this.quesContent = quesBoard.getQuesContent();
		this.quesRdate = quesBoard.getQuesRdate();
		this.quesFilename = quesBoard.getQuesFilename();
		this.quesFilepath = quesBoard.getQuesFilepath();
	}
}

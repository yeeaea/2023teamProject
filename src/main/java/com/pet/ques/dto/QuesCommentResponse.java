package com.pet.ques.dto;

import java.time.LocalDateTime;

import com.pet.ques.domain.QuesComment;

import lombok.Getter;

@Getter
public class QuesCommentResponse {
	private String quesCmtContent;
	private LocalDateTime quesCmtRdate;
	private LocalDateTime quesCmtUdate;
	
	public QuesCommentResponse(QuesComment quesComment) {
		
		this.quesCmtContent = quesComment.getQuesCmtContent();
		this.quesCmtRdate = quesComment.getQuesCmtRdate();
		this.quesCmtUdate = quesComment.getQuesCmtUdate();
	}
	
}

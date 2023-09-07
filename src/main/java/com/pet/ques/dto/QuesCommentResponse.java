package com.pet.ques.dto;

import com.pet.ques.domain.QuesComment;

import lombok.Getter;

@Getter
public class QuesCommentResponse {

	private String QuesCmtContent;
	
	public QuesCommentResponse(QuesComment quesComment) {
		this.QuesCmtContent = quesComment.getQuesCmtContent();
	}
	
}

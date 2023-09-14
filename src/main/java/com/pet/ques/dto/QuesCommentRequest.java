package com.pet.ques.dto;

import com.pet.ques.domain.QuesComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuesCommentRequest {
	private Long quesNo;
	private String quesCmtContent;

	public QuesComment toEntity() {
		return QuesComment.builder()
				.quesCmtContent(quesCmtContent)
				.build();
	}

}

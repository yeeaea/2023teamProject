package com.pet.ques.dto;


import com.pet.ques.domain.QuesComment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
@Setter
public class QuesCommentRequest {
	// private Long quesCmtNo;
	private Long quesNo;
	private String quesCmtContent;
	// private LocalDateTime quesCmtRdate;

	public QuesComment toEntity() {
		return QuesComment.builder()
				.quesCmtContent(quesCmtContent)
				.build();
	}

}

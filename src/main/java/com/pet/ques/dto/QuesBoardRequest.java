package com.pet.ques.dto;

import com.pet.ques.domain.QuesBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuesBoardRequest {

	private String quesTitle;
	private String quesContent;
	private String quesFilename;
	private String quesFilepath;
	
	public QuesBoard toEntity() {
		return QuesBoard.builder()
				.quesTitle(quesTitle)
				.quesContent(quesContent)
				.quesFilename(quesFilename)
				.quesFilepath(quesFilepath)
				.build();
	}
}

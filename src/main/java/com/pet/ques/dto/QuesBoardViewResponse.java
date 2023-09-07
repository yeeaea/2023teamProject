package com.pet.ques.dto;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.pet.ques.domain.QuesBoard;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
// 뷰에서 사용할 DTO
public class QuesBoardViewResponse {
	
	private Long quesNo;
	private String quesTitle;
	private String quesContent;
	private LocalDateTime quesRdate;
	private String quesFilename;
	private String quesFilepath;
	
//	private LocalDateTime quesUdate;
//	private String memId;
	
	public QuesBoardViewResponse(QuesBoard quesBoard) {
		this.quesNo = quesBoard.getQuesNo();
		this.quesTitle = quesBoard.getQuesTitle();
		this.quesContent = quesBoard.getQuesContent();
		this.quesRdate = quesBoard.getQuesRdate();
		this.quesFilename = quesBoard.getQuesFilename();
		this.quesFilepath = quesBoard.getQuesFilepath();
//		this.quesUdate = quesBoard.getQuesUdate();
//		this.memId = quesBoard.getMemId();
	}
}

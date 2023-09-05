package com.pet.ques.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
// 블로그 글 수정 요청을 받을 dto
public class UpdateQuesBoardRequestDto {

	private String quesTitle;
	private String quesContent;
	private Date quesRdate;
	private Date quesUdate;
//	private String memId;
}

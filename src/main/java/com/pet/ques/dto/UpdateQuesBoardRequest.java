package com.pet.ques.dto;

import java.util.Date;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
// 블로그 글 수정 요청을 받을 dto
public class UpdateQuesBoardRequest {

	private String quesTitle;
	private String quesContent;
	private Date quesRdate;
	private Date quesUdate;
	private String quesFilename;
	private String quesFilepath;
//	private String memId;
}

package com.pet.ques.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateQuesCommentRequest {
	private String quesCmtContent;
	private LocalDateTime quesCmtRdate;
	private LocalDateTime quesCmtUdate;
}

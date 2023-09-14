package com.pet.free.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateFreeCommentRequest {
	private String freeCmtContent;
	private LocalDateTime freeCmtRdate;
	private LocalDateTime freeCmtUdate;
}

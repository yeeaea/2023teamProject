package com.pet.free.dto;

import java.time.LocalDateTime;

import com.pet.free.domain.FreeBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddFreeBoardRequest {

	private String freeTitle;
	private String freeContent;
	private LocalDateTime freeRdate;
	private LocalDateTime freeUdate;
	private String memId;
	private String adminId;
	
	public FreeBoard toEntity() {
		return FreeBoard.builder()
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.freeRdate(freeRdate)
				.freeUdate(freeUdate)
				.memId(memId)
				.adminId(adminId)
				.build();
	}

}

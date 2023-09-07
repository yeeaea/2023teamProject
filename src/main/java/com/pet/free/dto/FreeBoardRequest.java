package com.pet.free.dto;

import com.pet.free.domain.FreeBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class FreeBoardRequest {

	private String freeTitle;
	private String freeContent;
	
	public FreeBoard toEntity() {
		return FreeBoard.builder()
				.freeTitle(freeTitle)
				.freeContent(freeContent)
				.build();
	}

}

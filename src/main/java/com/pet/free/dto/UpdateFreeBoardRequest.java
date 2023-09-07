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

public class UpdateFreeBoardRequest {
	private String freeTitle;
	private String freeContent;
	private LocalDateTime freeRdate;
	private LocalDateTime freeUdate;
	private String freeFilename;
	private String freeFilepath;
	
}

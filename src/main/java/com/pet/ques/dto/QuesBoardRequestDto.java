package com.pet.ques.dto;

import java.time.LocalDateTime;

import com.pet.ques.domain.QuesBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
// DTO는 계층끼리 데이터를 교환하기 위해 사용하는 객체
// 단순하게 데이터를 옮기기 위해 사용하는 전달자 역할을 함
// 요청 dto
public class QuesBoardRequestDto {

	
	private String quesTitle;
	private String quesContent;
//	private LocalDateTime quesRdate;
//	private LocalDateTime quesUdate;
//	private Integer quesViews;
//	private String memId;
	
	// toEntity()는 DTO를 엔티티로 만들어주는 메서드
	// QuesBoard가 엔티티이니까 결과값은 QuesBoard로 나와야 함
	public QuesBoard toEntity() { // 생성자를 사용해 객체 생성
		return QuesBoard.builder()
				.quesTitle(quesTitle)
				.quesContent(quesContent)
//				.quesRdate(quesRdate)
//				.quesUdate(quesUdate)
//				.quesViews(quesViews)
//				.memId(memId)
				.build();
	}
}

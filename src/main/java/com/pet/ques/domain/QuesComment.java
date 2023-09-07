package com.pet.ques.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)

public class QuesComment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ques_cmt_no", nullable = false)
	private Long quesCmtNo;
    
	@Column(name = "ques_no", nullable = false)
	private Long quesNo;
	
	@Column(name = "ques_cmt_content")
	private String quesCmtContent;
	
	@CreatedDate		// 엔티티가 생성될 때 생성 시간 저장
	@Column(name="ques_cmt_rdate")
	private LocalDateTime quesCmtRdate;
	
	@Builder
	public QuesComment(String quesCmtContent) {
		this.quesCmtContent = quesCmtContent;
	}
	
}

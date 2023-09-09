package com.pet.ques.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="ques_board")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="QUESBOARD_SEQ_GENERATOR", sequenceName="quesboard_seq", initialValue =1, allocationSize =1)
public class QuesBoard {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ques_no", nullable = false)
    private Long quesNo;
	
	@Column(name="ques_title", nullable=false)
	private String quesTitle;
	
	@Column(name="ques_content", nullable=false)
	private String quesContent;
	
	@CreatedDate		// 엔티티가 생성될 때 생성 시간 저장
	@Column(name="ques_rdate")
	private LocalDateTime quesRdate;
	
	@LastModifiedDate	// 엔티티가 수정될 때 수정 시간 설정
	@Column(name="ques_udate")
	private LocalDateTime quesUdate;
	
	@Column(name="ques_filename")
	private String quesFilename;
	
	@Column(name="ques_filepath")
	private String quesFilepath;
	
	@Column(name="ques_visit")
	private int quesVisit;
	
	
//	@Column(name="mem_id", nullable=false)
//	private String memId;
	
	@Builder
	public QuesBoard(String quesTitle, String quesContent, String quesFilename, String quesFilepath) {
		this.quesTitle = quesTitle;
		this.quesContent = quesContent;
		this.quesFilename = quesFilename;
		this.quesFilepath = quesFilepath;
//		this.quesRdate = quesRdate;
//		this.quesUdate = quesUdate;
//		this.memId = memId;
	}
	
	
	// 수정 메서드
	public void update(String quesTitle, String quesContent) {
		this.quesTitle = quesTitle;
		this.quesContent = quesContent;
//		this.quesFilename = quesFilename;
//		this.quesFilepath = quesFilepath;
//		this.quesRdate = quesRdate;
//		this.quesUdate = quesUdate;
	}

}


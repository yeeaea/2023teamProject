package com.pet.free.domain;

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
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class FreeBoard {
	
    @Id
    @GeneratedValue(
    	strategy = GenerationType.IDENTITY)
    @Column(name = "free_no", updatable = false)
	private Long freeNo;
	
	@Column(name="free_title", nullable=false)
    private String freeTitle;
    
    @Column(name="free_content", nullable=false)
    private String freeContent;
    
    @CreatedDate
    @Column(name="free_rdate")
    private LocalDateTime freeRdate;
    
    @LastModifiedDate
    @Column(name="free_udate")
    private LocalDateTime freeUdate;
    

    @Builder
	public FreeBoard(String freeTitle, String freeContent) {
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
	}
    
    // 수정 메서드
    public void update(String freeTitle, String freeContent) {
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
    }
    
}

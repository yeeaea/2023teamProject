package com.pet.free.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class FreeBoard {
	
	@SequenceGenerator(
			name = "seq_free_no",
			sequenceName = "seq_free_no",
			initialValue = 1,
			allocationSize = 1)
    @Id
    @GeneratedValue(
    	strategy = GenerationType.SEQUENCE,
    	generator = "seq_free_no")
    @Column(name = "free_no", updatable = false)
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="free_no", updatable=false)
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
    
    @Column(name="mem_id", nullable=false)
    private String memId;
    
    @Column(name="admin_id", nullable=false)
    private String adminId;

    @Builder
	public FreeBoard(String freeTitle, String freeContent
			, LocalDateTime freeRdate, LocalDateTime freeUdate,
			String memId, String adminId
			) {
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
		this.freeRdate = freeRdate;
		this.freeUdate = freeUdate;
		this.memId = memId;
		this.adminId = adminId;
	}
    
    public void update(String freeTitle, String freeContent) {
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
    }
    
}

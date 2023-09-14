package com.pet.free.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="free_board")
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class FreeBoard {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
	@Column(name="free_filename")
	private String freeFilename;
	
	@Column(name="free_filepath")
	private String freeFilepath;
	
	@Column(name="free_visit")
	private int freeVisit;
    
	 @OneToMany(mappedBy = "freeBoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	 private List<FreeComment> comments;
	
    @Builder
	public FreeBoard(String freeTitle, String freeContent, String freeFilename, String freeFilepath) {
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
		this.freeFilename = freeFilename;
		this.freeFilepath = freeFilepath;
	}
    
    // 수정 메서드
    public void update(String freeTitle, String freeContent) {
		this.freeTitle = freeTitle;
		this.freeContent = freeContent;
    }
    
}

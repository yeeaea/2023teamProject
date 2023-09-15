package com.pet.map.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="review")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class PlaceReview {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "review_no", nullable = false)
   private Long reviewNo;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "no")
   private Place place;
   
   @Column(name = "review_content")
   private String reviewContent;
   
   @Column(name="review_score")
   private float reviewScore;
   
   @CreatedDate
   @Column(name = "review_rdate")
   private LocalDateTime reviewRdate;
  
   @Builder
   public PlaceReview(String reviewContent) {
      this.reviewContent = reviewContent;
   }
   
   public void update(String reviewContent) {
      this.reviewContent = reviewContent;
   }
}
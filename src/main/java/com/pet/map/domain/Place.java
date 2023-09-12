package com.pet.map.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name="place")
@Entity
@Getter
@Setter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Place {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)   
   @Column(name="no", updatable = false)
   private Long no;
   
   @Column(name="type", nullable = false)
   private String type;
   
   @Column(name="name", nullable = false)
   private String name;
   
   @Column(name="tel")
   private String tel;
   
   @Column(name="addr", nullable = false)
   private String addr;
   
   @Column(name="sido", nullable = false)
   private String sido;
   
   @Column(name="gugun", nullable = false)
   private String gugun;
   
   @Column (name="time")
   private String time;
   
   @Column(name="lat", nullable= false)
   private double lat;
   
   @Column(name="lon", nullable = false)
   private double lon;

}
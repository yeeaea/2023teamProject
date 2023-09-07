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

@Table(name="funeral")
@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class FuneralMap {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)   
   @Column(name="funeral_no", updatable = false)
   private Long funNo;
   
   @Column(name="funeral_name", nullable= false)
   private String funName;
   
   @Column(name="funeral_tel")
   private String funTel;
   
   @Column(name="funeral_addr", nullable = false)
   private String funAddr;
   
   @Column (name="funeral_time")
   private String funTime;
   
   @Column(name="fun_lat", nullable= false)
   private double funLat;
   
   @Column(name="fun_lon", nullable = false)
   private double funLon;

}
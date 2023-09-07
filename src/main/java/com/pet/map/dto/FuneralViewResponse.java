package com.pet.map.dto;

import com.pet.map.domain.FuneralMap;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
public class FuneralViewResponse {
	
	   private final Long funNo;
	   private final String funName;
	   private final String funTel;
	   private final String funAddr;
	   private final String funTime;
	   private final double funLat;
	   private final double funLon;
	   
	   
	public FuneralViewResponse(FuneralMap funeralMap) {
		this.funNo = funeralMap.getFunNo();
		this.funName = funeralMap.getFunName();
		this.funTel = funeralMap.getFunTel();
		this.funAddr = funeralMap.getFunAddr();
		this.funTime = funeralMap.getFunTime();
		this.funLat = funeralMap.getFunLat();
		this.funLon = funeralMap.getFunLon();
	}
	   
	   
}

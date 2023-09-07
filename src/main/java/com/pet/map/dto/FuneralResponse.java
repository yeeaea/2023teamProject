package com.pet.map.dto;

import com.pet.map.domain.FuneralMap;

import lombok.Getter;

@Getter
public class FuneralResponse {

   private final double funLat;
   private final double funLon;
   
   public FuneralResponse(FuneralMap funeralMap) {
      this.funLat = funeralMap.getFunLat();
      this.funLon = funeralMap.getFunLon();
   }
}
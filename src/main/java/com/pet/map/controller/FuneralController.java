package com.pet.map.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pet.map.dto.FuneralResponse;
import com.pet.map.service.FuneralService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class FuneralController {

   private final FuneralService funeralService;
   
   @GetMapping("/api/funerals")
   public ResponseEntity<List<FuneralResponse>> findAllFunerals(){
      List<FuneralResponse> funerals = funeralService.findAll()
            .stream()
            .map(FuneralResponse::new)
            .toList();
      
      return ResponseEntity.ok()
            .body(funerals);
   }
}
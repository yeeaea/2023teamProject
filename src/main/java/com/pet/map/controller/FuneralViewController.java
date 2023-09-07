package com.pet.map.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.pet.map.dto.FuneralViewRsponse;
import com.pet.map.service.FuneralService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FuneralViewController {
	
	 private final FuneralService funeralService;
	 
	@GetMapping("/map")
	public String getFunerals(Model model) {
		List<FuneralViewRsponse> funerals =  funeralService.findAll().stream()
				.map(FuneralViewRsponse::new)
				.toList();
		model.addAttribute("funerals", funerals);
		
		return "map";
	}
	
}

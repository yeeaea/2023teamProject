package com.pet.free.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.FreeBoardListViewResponse;
import com.pet.free.dto.FreeBoardViewResponse;
import com.pet.free.service.FreeBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {
	
	@Autowired
	private final FreeBoardService freeBoardService;
	
	@GetMapping("/")
	public String main() {
		return "index";
	}
	
	@GetMapping("/freeboards")
	public String getFreeBoard(@PageableDefault(size=10)Pageable pageable, Model model) {
		Page<FreeBoard> freeBoard = freeBoardService.findAll(pageable);
		
		int startPage = Math.max(1, freeBoard.getPageable().getPageNumber()-9);
		int endPage = Math.min(freeBoard.getPageable().getPageNumber() + 10, freeBoard.getTotalPages());
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
	
		model.addAttribute("freeBoard", freeBoard);
		
		return "freeBoardList";
	}
	
	@GetMapping("/freeboards/{freeNo}")
	public String getFreeBoard(@PathVariable Long freeNo, Model model) {
		FreeBoard freeBoard = freeBoardService.findById(freeNo);
		model.addAttribute("freeBoard", new FreeBoardViewResponse(freeBoard));
		
		return "freeBoard";
	}
	
	@GetMapping("/new-freeboard")
	public String newFreeBoard(@RequestParam(required = false) Long freeNo, Model model) {
		if(freeNo == null) {
			model.addAttribute("freeBoard", new FreeBoardViewResponse());
		} else {
			FreeBoard freeBoard = freeBoardService.findById(freeNo);
			model.addAttribute("freeBoard", new FreeBoardViewResponse(freeBoard));
		}
		
		return "newFreeBoard";
	}
	
}

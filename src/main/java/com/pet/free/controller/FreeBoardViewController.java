package com.pet.free.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.FreeBoardViewResponse;
import com.pet.free.service.FreeBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {
	
	private final FreeBoardService freeBoardService;
	
	@GetMapping("/")
	public String main() {
		return "main";
	}
	
	@GetMapping("/freeboards")
	public String getFreeBoardAndSearch(@PageableDefault(size = 10, sort = "freeNo", direction = Sort.Direction.DESC)Pageable pageable, Model model, @RequestParam(required = false) String keyword) {
		Page<FreeBoard> freeBoard;
		
		if(keyword == null) {
			// 검색어가 없을 때는 전체 목록을 가져옵니다.
			freeBoard = freeBoardService.findAll(pageable);			
		} else {
			// 검색어가 있을 때는 검색을 실행합니다.
			freeBoard = freeBoardService.boardSearchList(keyword, pageable);
		}
		
		int startPage = Math.max(1, freeBoard.getPageable().getPageNumber()-9);
		int endPage = Math.min(freeBoard.getPageable().getPageNumber() + 9, freeBoard.getTotalPages());
		
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("freeBoard", freeBoard);
		
		return "/board/freeBoardList.html";
	}
	
	@GetMapping("/freeboards/{freeNo}")
	public String getFreeBoard(@PathVariable Long freeNo, Model model) {
		FreeBoard freeBoard = freeBoardService.getFree(freeNo);
		model.addAttribute("freeBoard", new FreeBoardViewResponse(freeBoard));
		
		return "/board/freeBoard.html";
	}
	
	@GetMapping("/new-freeboard")
	public String newFreeBoard(@RequestParam(required = false) Long freeNo, Model model) {
		if(freeNo == null) {
			model.addAttribute("freeBoard", new FreeBoardViewResponse());
		} else {
			FreeBoard freeBoard = freeBoardService.findById(freeNo);
			model.addAttribute("freeBoard", new FreeBoardViewResponse(freeBoard));
		}
		
		return "/board/newFreeBoard.html";
	}
	
}

package com.pet.free.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {
	
	private final FreeBoardService freeBoardService;
	
	@GetMapping("/freeboards")
	public String getFreeBoardAndSearch(@PageableDefault(size = 10) Pageable pageable,
            Model model,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sortBy) {
		Page<FreeBoard> freeBoard;
		
		 if (keyword == null) {
		        if ("most-visited".equals(sortBy)) {
		            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("freeVisit").descending());
		            freeBoard = freeBoardService.findAllByOrderByFreeVisitDesc(pageable); // 조회순 정렬
		        } else {
		            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("freeRdate").descending());
		            freeBoard = freeBoardService.findAll(pageable); // 최신순 정렬 (기본)
		        }
		    } else {
		    	 freeBoard = freeBoardService.boardSearchList(keyword, pageable);
		    }

		    int startPage = Math.max(1, freeBoard.getPageable().getPageNumber() - 4);
		    int endPage = Math.min(freeBoard.getPageable().getPageNumber() + 4, freeBoard.getTotalPages());

		    model.addAttribute("freeBoard", freeBoard);
		    model.addAttribute("startPage", startPage);
		    model.addAttribute("endPage", endPage);
		    model.addAttribute("sortBy", sortBy);

		return "/board/freeBoardList.html";
	}
	
	@GetMapping("/freeboards/{freeNo}")
	public String getFreeBoard(@PathVariable Long freeNo, Model model, HttpSession session) {
		FreeBoard freeBoard = freeBoardService.getFree(freeNo, session);
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

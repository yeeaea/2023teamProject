package com.pet.free.controller;

import java.util.List;
import java.util.stream.Collectors;

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
import com.pet.free.domain.FreeComment;
import com.pet.free.dto.FreeBoardViewResponse;
import com.pet.free.dto.FreeCommentListViewResponse;
import com.pet.free.service.FreeBoardService;
import com.pet.free.service.FreeCommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class FreeBoardViewController {
	
	private final FreeBoardService freeBoardService;
	private final FreeCommentService freeCommentService;
	
	
	@GetMapping("/freeboards/{freeNo}")
	public String getFreeBoard(@PathVariable Long freeNo, Model model, HttpSession session) {
		FreeBoard freeBoard = freeBoardService.getFree(freeNo, session);
		
		// 댓글 목록을 가져오기
        List<FreeComment> comments =
        		freeCommentService.getCommentsByFreeNo(freeNo);
        List<FreeCommentListViewResponse> commentResponses =
        		comments.stream()
                .map(FreeCommentListViewResponse::new)
                .collect(Collectors.toList());

		model.addAttribute("freeBoard", freeBoard);
		model.addAttribute("comments", commentResponses);
		
		return "board/freeBoard.html";
	}
	
	@GetMapping("/new-freeboard")
	public String newFreeBoard(@RequestParam(required = false) Long freeNo, Model model) {
		if(freeNo == null) {
			model.addAttribute("freeBoard", new FreeBoardViewResponse());
		} else {
			FreeBoard freeBoard = freeBoardService.findById(freeNo);
			model.addAttribute("freeBoard", new FreeBoardViewResponse(freeBoard));
		}
		
		return "board/newFreeBoard.html";
	}
	
	
	@GetMapping("/freeboards")
	public String getFreeBoardAndSearch(@PageableDefault(size = 10) Pageable pageable, Model model,
            @RequestParam(required = false) String keyword,  @RequestParam(required = false) String sortBy) {
		Page<FreeBoard> boards;
		
		 if (keyword == null) {
		        if ("most-visited".equals(sortBy)) {
		        	// 조회순 정렬
		            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
		            		Sort.by("freeVisit").descending());
		            boards = freeBoardService.findAllByOrderByFreeVisitDesc(pageable);
		       
		        } else {
		        	// 최신순 정렬
		            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
		            		Sort.by("freeRdate").descending());
		            boards = freeBoardService.findAll(pageable);
		        }
		        
		    } else {
		    	boards = freeBoardService.boardSearchList(keyword, pageable);
		    }

		    int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
		    int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());

		    model.addAttribute("freeBoard", boards);
		    model.addAttribute("startPage", startPage);
		    model.addAttribute("endPage", endPage);
		    model.addAttribute("sortBy", sortBy);

		return "board/freeBoardList.html";
	}
	
	
	
}

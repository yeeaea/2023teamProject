package com.pet.ques.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.dto.QuesBoardViewResponse;
import com.pet.ques.service.QuesBoardService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuesBoardViewController {

	private final QuesBoardService quesBoardService;
	
//	@GetMapping("/questions")
//	public String getQuestions(@PageableDefault(size=5)Pageable pageable, Model model) {
//		Page<QuesBoard> boards = quesBoardService.findAll(pageable);
//		
//		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
//	    int endPage = Math.min(boards.getPageable().getPageNumber()+4, boards.getTotalPages());
//	 
//	    model.addAttribute("startPage", startPage);
//	    model.addAttribute("endPage", endPage);
//		
//		model.addAttribute("questions", boards); // 블로그 글 리스트 저장
//		
//		return "questionList"; // questionList.html 라는 뷰 조회
//	}
	
	@GetMapping("/questions/{quesNo}")
	public String getQuestions(@PathVariable Long quesNo, Model model) {
	    QuesBoard question = quesBoardService.getQues(quesNo);
	    if (question != null) {
	       
	        model.addAttribute("question", new QuesBoardViewResponse(question));

	        return "/board/question.html";
	    } else {
	        // 게시물이 없을 경우 예외 처리 또는 다른 대응
	        return "redirect:/not_found"; // 예시: 존재하지 않는 게시물 처리
	    }
	}

	
	@GetMapping("/new-question")
	// quesNo키를 가진 쿼리 파라미터의 값을 quesNo 변수에 매핑(quesNo는 없을 수도 있음)
	public String newQuestion (@RequestParam(required=false) Long quesNo, Model model) {
		if(quesNo == null) {	// quesNo가 없으면 생성
			model.addAttribute("question", new QuesBoardViewResponse());
		}else {					// quesNo가 없으면 수정
			QuesBoard question = quesBoardService.findById(quesNo);
			model.addAttribute("question", new QuesBoardViewResponse(question));
		}
		return "/board/newQuestion.html";
	}
	
	@GetMapping("/questions")
	public String getQuestionsAndSearch(@PageableDefault(size = 5) Pageable pageable, Model model, @RequestParam(required = false) String keyword) {
	    Page<QuesBoard> boards;

	    if (keyword == null) {
	        // 검색어가 없을 때는 전체 목록을 가져옵니다.
	        boards = quesBoardService.findAll(pageable);
	    } else {
	        // 검색어가 있을 때는 검색을 실행합니다.
	        boards = quesBoardService.boardSearchList(keyword, pageable);
	    }

	    int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
	    int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());

	    model.addAttribute("questions", boards);
	    model.addAttribute("startPage", startPage);
	    model.addAttribute("endPage", endPage);

	    return "/board/questionList.html";
	}

}
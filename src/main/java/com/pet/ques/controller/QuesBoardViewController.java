package com.pet.ques.controller;

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

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.domain.QuesComment;
import com.pet.ques.dto.QuesBoardViewResponse;
import com.pet.ques.dto.QuesCommentListViewResponse;
import com.pet.ques.service.QuesBoardService;
import com.pet.ques.service.QuesCommentService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class QuesBoardViewController {

	private final QuesBoardService quesBoardService;
	private final QuesCommentService quesCommentService;

	// 게시글 보기
	@GetMapping("/questions/{quesNo}")
	public String showQuestion(@PathVariable Long quesNo, Model model, HttpSession session) {
		QuesBoard question = quesBoardService.getQues(quesNo, session);

		// 댓글 목록을 가져오기
		List<QuesComment> comments =
				quesCommentService.getCommentsByQuesNo(quesNo);
		List<QuesCommentListViewResponse> commentResponses =
				comments.stream()
				.map(QuesCommentListViewResponse::new)
				.collect(Collectors.toList());

		model.addAttribute("question", question);
		model.addAttribute("comments", commentResponses);

		return "/board/question.html";
	}

	// 게시글 수정
	@GetMapping("/new-question")
	public String newQuestion(@RequestParam(required = false) Long quesNo, Model model) {
		if (quesNo == null) {
			model.addAttribute("question", new QuesBoardViewResponse());
		} else {
			QuesBoard question = quesBoardService.findById(quesNo);
			model.addAttribute("question", new QuesBoardViewResponse(question));
		}
		return "/board/newQuestion.html";
	}

	// 게시판 글 형식
	@GetMapping("/questions")
	public String getQuestionsAndSearch(@PageableDefault(size = 10) Pageable pageable, Model model,
			@RequestParam(required = false) String keyword, @RequestParam(required = false) String sortBy) {
		Page<QuesBoard> boards;

		if (keyword == null) {

			if ("most-visited".equals(sortBy)) {
				// 조회순 정렬
				pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
						Sort.by("quesVisit").descending());
				boards = quesBoardService.findAllByOrderByQuesVisitDesc(pageable);

			} else {
				// 최신순 정렬
				pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
						Sort.by("quesRdate").descending());
				boards = quesBoardService.findAll(pageable);
			}

		} else {
			boards = quesBoardService.boardSearchList(keyword, pageable);
		}

		int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4);
		int endPage = Math.min(boards.getPageable().getPageNumber() + 4, boards.getTotalPages());

		model.addAttribute("questions", boards);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("sortBy", sortBy);

		return "/board/questionList.html";
	}

}
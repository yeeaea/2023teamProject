package com.pet.ques.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pet.ques.domain.QuesBoard;
import com.pet.ques.dto.QuesBoardRequest;
import com.pet.ques.repository.QuesBoardRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuesBoardService {

	@Autowired
	private final QuesBoardRepository quesBoardRepo;

	// 글 등록 (이미지 업로드)
	public QuesBoard save(QuesBoardRequest dto, MultipartFile file) throws Exception {
		String projectPath =
				System.getProperty("user.dir") + "/src/main/resources/static/files";

		// 파일이 없는 경우에 대한 처리 추가
		if (file != null && !file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			File saveFile = new File(projectPath, fileName);
			file.transferTo(saveFile);
			dto.setQuesFilename(fileName);
			dto.setQuesFilepath("/files/" + fileName);

		}
		return quesBoardRepo.save(dto.toEntity());
	}

	// 글 목록 조회
	public Page<QuesBoard> findAll(Pageable pageable) {
		Pageable Pageable =
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "quesNo"));
		
		return quesBoardRepo.findAll(Pageable);
	}

	// 조회수 정렬
	public Page<QuesBoard> findAllByOrderByQuesVisitDesc(Pageable pageable) {
		Pageable descendingPageable =
				PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "quesVisit"));
		
		return quesBoardRepo.
				findAllByOrderByQuesVisitDesc(descendingPageable);
	}

	// 글 하나 조회
	public QuesBoard findById(Long quesNo) {
		return quesBoardRepo.findById(quesNo)
				.orElseThrow(() ->
					new IllegalArgumentException(quesNo + "번 글이 존재하지 않습니다."));
	}

	// 글 삭제
	public void delete(Long quesNo) {
		quesBoardRepo.deleteById(quesNo);
	}

	// 글 수정
	@Transactional
	public QuesBoard update(Long quesNo, String quesTitle,
			String quesContent, MultipartFile file) throws IOException {
		QuesBoard quesBoard = quesBoardRepo.findById(quesNo)
				.orElseThrow(() ->
					new IllegalArgumentException(quesNo + "번 글이 존재하지 않습니다."));
		quesBoard.setQuesRdate(LocalDateTime.now());
		quesBoard.update(quesTitle, quesContent);

		String projectPath =
				System.getProperty("user.dir") + "/src/main/resources/static/files";

		// 파일이 없는 경우에 대한 처리 추가
		if (file != null && !file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			File saveFile = new File(projectPath, fileName);
			file.transferTo(saveFile);
			quesBoard.setQuesFilename(fileName);
			quesBoard.setQuesFilepath("/files/" + fileName);
		}
		// 수정된 엔티티를 저장하고 반환
		return quesBoardRepo.save(quesBoard);
	}

	// 키워드 검색
	public Page<QuesBoard> boardSearchList(String keyword, Pageable pageable) {
		return quesBoardRepo.findByquesTitleContaining(keyword, pageable);
	}
	
	// 세션을 통한 중복 방지 조회수 증가
	public QuesBoard getQues(long quesNo, HttpSession session) {
		// 게시물 조회
		Optional<QuesBoard> ques = quesBoardRepo.findById(quesNo);
		if (ques.isPresent()) {
			QuesBoard question = ques.get();

			// 세션에 이미 조회한 게시물을 추적하는 키를 생성
			String visitedKey = "visited_question_" + quesNo;

			// 세션에서 해당 키의 값(게시물을 이미 조회한 경우)을 가져옴
			Boolean hasVisited = (Boolean) session.getAttribute(visitedKey);

			if (hasVisited == null || !hasVisited) {
				// 사용자가 해당 게시물을 아직 조회하지 않은 경우에만 조회수 증가
				question.setQuesVisit(question.getQuesVisit() + 1);
				// 데이터베이스 업데이트
				quesBoardRepo.save(question);
				// 세션에 해당 키를 저장하여 중복 조회수 증가를 방지
				session.setAttribute(visitedKey, true);
			}
			return question;
		} else {
			return null;
		}
	}
	
	// 글 작성자의 닉네임을 가져오는 메소드
	public String getNickname(Long quesNo) {
		QuesBoard quesBoard = quesBoardRepo.findById(quesNo).orElse(null);
		
		if (quesBoard != null) {
			return quesBoard.getNickname();
		}
		return null;
	}
}
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

// final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@RequiredArgsConstructor
// 빈으로 등록
@Service
public class QuesBoardService {

	@Autowired
	private final QuesBoardRepository quesBoardRepo;

//  // 블로그 글 추가 메서드
//	public QuesBoard save(QuesBoardRequestDto dto) {
//		return quesBoardRepo.save(dto.toEntity());
//	}

	// 글 추가 메서드 (이미지 업로드)
	public QuesBoard save(QuesBoardRequest dto, MultipartFile file) throws Exception {
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

		// 파일이 없는 경우에 대한 처리 추가
		if (file != null && !file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			File saveFile = new File(projectPath, fileName);
			file.transferTo(saveFile);
			dto.setQuesFilename(fileName);
			dto.setQuesFilepath("/files/" + fileName);
		} else {
			// 파일이 없는 경우, 파일 관련 정보를 null로 설정
			dto.setQuesFilename(null);
			dto.setQuesFilepath(null);
		}

		return quesBoardRepo.save(dto.toEntity());
	}

	// JPA의 지원메서드인 findAll()을 호출해 QuesBoard 테이블에 저장되어 있는 모든 데이터를 조회합니다.
	public Page<QuesBoard> findAll(Pageable pageable) {
		Pageable Pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "quesNo"));
		return quesBoardRepo.findAll(Pageable);
	}
	
	public Page<QuesBoard> getPostsOrderByVisit(Pageable pageable) {
		Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "quesVisit"));
	        return quesBoardRepo.findAllByOrderByQuesVisitDesc(descendingPageable);
	    }


//  데이터베이스에 저장되어 있는 글의 No를 이용해 글을 하나 조회하기
	public QuesBoard findById(long quesNo) {
		return quesBoardRepo.findById(quesNo)
				.orElseThrow(() -> new IllegalArgumentException(quesNo + "번 글이 존재하지 않습니다."));
	}

	// 글의 no을 받은 뒤 JPA에서 제공하는 deleteById() 메서드를 이용해 데이터 삭제
	public void delete(long quesNo) {
		quesBoardRepo.deleteById(quesNo);
	}

	// 글 수정 메서드
	@Transactional
	public QuesBoard update(long quesNo, String quesTitle, String quesContent, MultipartFile file) throws IOException {
		QuesBoard quesBoard = quesBoardRepo.findById(quesNo)
				.orElseThrow(() -> new IllegalArgumentException(quesNo + "번 글이 존재하지 않습니다."));
		quesBoard.setQuesRdate(LocalDateTime.now());
		quesBoard.update(quesTitle, quesContent);

    
		String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";


		// 파일이 없는 경우에 대한 처리 추가
		if (file != null && !file.isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			File saveFile = new File(projectPath, fileName);
			file.transferTo(saveFile);
			quesBoard.setQuesFilename(fileName);
			quesBoard.setQuesFilepath("/files/" + fileName);
		} 
		return quesBoardRepo.save(quesBoard); // 수정된 엔티티를 저장하고 반환
	}

	// 키워드 검색
	public Page<QuesBoard> boardSearchList(String keyword, Pageable pageable) {
		return quesBoardRepo.findByquesTitleContaining(keyword, pageable);
	}

	// 세션을 통한 중복 방지 조회수 증가
	@Transactional
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
	            quesBoardRepo.save(question); // 데이터베이스 업데이트
	            // 세션에 해당 키를 저장하여 중복 조회수 증가를 방지
	            session.setAttribute(visitedKey, true);
	        }
	        return question;
	        
	    } else {
	        return null;
	    }
	    
	}   
}
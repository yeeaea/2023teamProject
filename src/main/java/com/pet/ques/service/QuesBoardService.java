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
import com.pet.ques.dto.UpdateQuesBoardRequest;
import com.pet.ques.repository.QuesBoardRepository;

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
	public Page<QuesBoard> findAll(Pageable pageable){
	    Pageable descendingPageable = PageRequest.of(
	        pageable.getPageNumber(),
	        pageable.getPageSize(),
	        Sort.by(Sort.Direction.DESC, "quesNo"));
	    return quesBoardRepo.findAll(descendingPageable);
	}
	
	// 데이터베이스에 저장되어 있는 글의 No를 이용해 글을 하나 조회하기
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
	 public Page<QuesBoard> boardSearchList(String keyword, Pageable pageable){
	    return quesBoardRepo.findByquesTitleContaining(keyword, pageable);
	}
	 
	 // 조회수 증가.. 
	 @Transactional
	 public QuesBoard getQues(long quesNo) {
	        Optional<QuesBoard> ques = quesBoardRepo.findById(quesNo);
	        if (ques.isPresent()) {
	            QuesBoard question = ques.get();
	            question.setQuesVisit(question.getQuesVisit() + 1); // 조회수 증가
	            quesBoardRepo.save(question); // 데이터베이스 업데이트
	            return question;
	        } else {
	            return null;
	        }
	  }

//	   
}

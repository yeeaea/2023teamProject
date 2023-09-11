package com.pet.free.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pet.free.domain.FreeBoard;
import com.pet.free.dto.FreeBoardRequest;
import com.pet.free.dto.UpdateFreeBoardRequest;
import com.pet.free.repository.FreeBoardRepository;
import com.pet.ques.domain.QuesBoard;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FreeBoardService {

	private final FreeBoardRepository freeBoardRepository;
	
	// 글 추가 메서드 (이미지 업로드)
	public FreeBoard save(FreeBoardRequest request, MultipartFile file) throws Exception {
	    String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

	    // 파일이 없는 경우에 대한 처리 추가
	    if (file != null && !file.isEmpty()) {
	        UUID uuid = UUID.randomUUID();
	        String fileName = uuid + "_" + file.getOriginalFilename();
	        File saveFile = new File(projectPath, fileName);
	        file.transferTo(saveFile);        
	        request.setFreeFilename(fileName);
	        request.setFreeFilepath("/files/" + fileName);
	    } else {
	        // 파일이 없는 경우, 파일 관련 정보를 null로 설정
	    	request.setFreeFilename(null);
	        request.setFreeFilepath(null);
	    }
		return freeBoardRepository.save(request.toEntity());
	}
	
	public Page<FreeBoard> findAll(Pageable pageable){
		Pageable descendingPageable = PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				Sort.by(Sort.Direction.DESC, "freeNo"));
		return freeBoardRepository.findAll(descendingPageable);
	}
	
	// 조회수 정렬
		public Page<FreeBoard> findAllByOrderByFreeVisitDesc(Pageable pageable) {
			Pageable descendingPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
					Sort.by(Sort.Direction.DESC, "freeVisit"));
		        return freeBoardRepository.findAllByOrderByFreeVisitDesc(descendingPageable);
		    }


	public FreeBoard findById(Long freeNo) {
		return freeBoardRepository.findById(freeNo)
				.orElseThrow(()-> new IllegalArgumentException("not found: " + freeNo));
	}
	
	public void delete(Long freeNo) {
		freeBoardRepository.deleteById(freeNo);
	}

	// 글 수정 메서드
		@Transactional
		public FreeBoard update(long freeNo, String freeTitle, String freeContent, MultipartFile file) throws IOException {
		    FreeBoard freeBoard = freeBoardRepository.findById(freeNo)
		            .orElseThrow(() -> new IllegalArgumentException(freeNo + "번 글이 존재하지 않습니다."));
		    freeBoard.setFreeRdate(LocalDateTime.now());
		    freeBoard.update(freeTitle, freeContent);

		    String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";

		    // 파일이 없는 경우에 대한 처리 추가
		    if (file != null && !file.isEmpty()) {
		        UUID uuid = UUID.randomUUID();
		        String fileName = uuid + "_" + file.getOriginalFilename();
		        File saveFile = new File(projectPath, fileName);
		        file.transferTo(saveFile);        
		        freeBoard.setFreeFilename(fileName);
		        freeBoard.setFreeFilepath("/files/" + fileName);
		    }

		    return freeBoardRepository.save(freeBoard); // 수정된 엔티티를 저장하고 반환
		}

	// 키워드 검색
	 public Page<FreeBoard> boardSearchList(String keyword, Pageable pageable){
	    return freeBoardRepository.findByfreeTitleContaining(keyword, pageable);
	}
	 
	 @Transactional
	 public FreeBoard getFree(long freeNo) {
	        Optional<FreeBoard> free = freeBoardRepository.findById(freeNo);
	        if (free.isPresent()) {
	            FreeBoard freeboard = free.get();
	            freeboard.setFreeVisit(freeboard.getFreeVisit() + 1); // 조회수 증가
	            freeBoardRepository.save(freeboard); // 데이터베이스 업데이트
	            return freeboard;
	        } else {
	            return null;
	        }
	  }
//	   
}

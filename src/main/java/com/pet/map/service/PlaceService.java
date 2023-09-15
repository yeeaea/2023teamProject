package com.pet.map.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pet.map.domain.Place;
import com.pet.map.repository.PlaceRepository;

@Service
public class PlaceService {

	private final PlaceRepository placeRepository;

    @Autowired
    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    public List<String> getDistinctTypes() {
        return placeRepository.findDistinctTypes();
    }

    public List<String> getDistinctSidosByType(String type) {
        return placeRepository.findDistinctSidosByType(type);
    }

    public List<String> getDistinctGugunsByTypeAndSido(String type, String sido) {
        return placeRepository.findDistinctGugunsByTypeAndSido(type, sido);
    }
    
    public List<Place> searchPlaces(String type, String sido, String gugun) {
        // PlaceRepository를 사용하여 데이터베이스에서 업체 정보 검색
        List<Place> places = placeRepository.findByTypeAndSidoAndGugun(type, sido, gugun);

        return places;
    }
    
    public List<Place> findAll(){
    	List<Place> places = placeRepository.findAll();
    	
    	return places;
    }
    
    public Place getPlaceByNo(Long no) {
    	// plaeRepository를 사용하여 데이터베이스에서 Place조회
    	Optional<Place> placeOptional = placeRepository.findById(no);
    	
    	// Optional을 사용하여 Place를 반환하거나 null을 반환
    	return placeOptional.orElse(null);
    }
}

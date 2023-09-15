
package com.pet.map.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pet.map.domain.Place;
import com.pet.map.domain.PlaceReview;
import com.pet.map.dto.ReviewListViewResponse;
import com.pet.map.service.PlaceService;
//import com.pet.map.service.ReviewService;
import com.pet.map.service.ReviewService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PlaceController {

	private final ReviewService reviewService;
	@Autowired
	private PlaceService placeService;
	
	@GetMapping("/")
	public String main(Model model) {
		List<String> types = placeService.getDistinctTypes();
		model.addAttribute("types", types);
		return "main";
	}
	
	@GetMapping("/api/places/types")
    public ResponseEntity<List<String>> getTypes() {
        List<String> types = placeService.getDistinctTypes();
        return ResponseEntity.ok(types);
    }
	

    @GetMapping("/api/places/sidos")
    public ResponseEntity<List<String>> getSido(@RequestParam String type) {
        List<String> sidos = placeService.getDistinctSidosByType(type);
        return ResponseEntity.ok(sidos);
    }

    @GetMapping("/api/places/guguns")
    public ResponseEntity<List<String>> getGuguns(@RequestParam String type, @RequestParam String sido) {
        List<String> guguns = placeService.getDistinctGugunsByTypeAndSido(type, sido);
        return ResponseEntity.ok(guguns);
    }	
    
    @GetMapping("/search")
    public String searchPlaces(@RequestParam String type,
                               @RequestParam String sido,
                               @RequestParam String gugun,
                               Model model) {
        // 서비스 레이어에서 필요한 데이터를 가져와서 모델에 추가
        List<Place> places = placeService.searchPlaces(type, sido, gugun);
        model.addAttribute("places", places);
        
        // 뷰 이름을 반환 (search.html)
        return "/map/search";
    }
    
    @GetMapping("/api/places/search")
    @ResponseBody
    public ResponseEntity<List<Place>> searchPlaces(@RequestParam String type,
            @RequestParam String sido,
            @RequestParam String gugun) {
	// 서비스 레이어에서 필요한 데이터를 가져와서 JSON으로 변환
	List<Place> places = placeService.searchPlaces(type, sido, gugun);
	
	// JSON 데이터와 HTTP 상태코드 반환
	return ResponseEntity.ok(places);
	}
   
    @GetMapping("/review/{no}")
    public String review(@PathVariable Long no, Model model) {
    	// PlaceService를 사용하여 모든 Place의 값을 가져오고, 뷰에 전달
    	// List<Place> places = placeService.findAll();
    	Place place = placeService.getPlaceByNo(no);
    	
    	List<PlaceReview> reviews = 
    			reviewService.getReviewsByNo(no);
    	List<ReviewListViewResponse> reveiwResponses = 
    			reviews.stream()
    			.map(ReviewListViewResponse::new)
    			.collect(Collectors.toList());
    	
    	model.addAttribute("place", place);
    	model.addAttribute("reviews", reveiwResponses);
    	
    	return "/map/review";
    }
}

package com.pet.security.controller;



import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pet.security.dto.MemberJoinDto;
import com.pet.security.service.RegisterMemberService;

import jakarta.validation.Valid;


@RestController
public class AuthorizationController {

    private final RegisterMemberService registerMemberService;

    public AuthorizationController(RegisterMemberService registerMemberService) {
        this.registerMemberService = registerMemberService;
    }


    @PostMapping("/auth/join")
    public ResponseEntity<String> join(@Valid @RequestBody MemberJoinDto dto, BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
    	    // 유효성 검사 오류가 있을 때 처리
    	    List<ObjectError> errors = bindingResult.getAllErrors();
    	    StringBuilder errorMessage = new StringBuilder("\n");
    	    
    	    for (ObjectError error : errors) {
    	        errorMessage.append(error.getDefaultMessage()).append("\n");
    	    }
    	    
    	    return ResponseEntity.badRequest().body(errorMessage.toString());
    	}
    	
	 try {
	        registerMemberService.join(dto.getUserid(), dto.getPassword(), dto.getNickname(), dto.getEmail());
	        return ResponseEntity.ok("join success");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
    }
}

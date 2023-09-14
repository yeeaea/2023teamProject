package com.pet.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pet.security.service.MemberCheckService;

@RestController
@RequestMapping("/api/valid")
public class MemberCheckController {

	private final MemberCheckService memberCheckService;
	
	@Autowired
	public MemberCheckController(MemberCheckService memberCheckService) {
		this.memberCheckService = memberCheckService;
	}
	
	@GetMapping("/checkUserid")
    public ResponseEntity<String> checkUserid(@RequestParam String userid) {
        boolean isUnique = memberCheckService.isUseridUnique(userid);
        if (isUnique) {
            return ResponseEntity.ok("사용 가능한 아이디입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용 중인 아이디입니다.");
        }
    }

    @GetMapping("/checkNickname")
    public ResponseEntity<String> checkNickname(@RequestParam String nickname) {
        boolean isUnique = memberCheckService.isNicknameUnique(nickname);
        if (isUnique) {
            return ResponseEntity.ok("사용 가능한 닉네임입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용 중인 닉네임입니다.");
        }
    }

    @GetMapping("/checkEmail")
    public ResponseEntity<String> checkEmail(@RequestParam String email) {
        boolean isUnique = memberCheckService.isEmailUnique(email);
        if (isUnique) {
            return ResponseEntity.ok("사용 가능한 이메일입니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 사용 중인 이메일입니다.");
        }
    }
}


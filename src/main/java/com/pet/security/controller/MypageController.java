package com.pet.security.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pet.security.domain.Member;
import com.pet.security.service.MemberService;

@Controller
public class MypageController {

	private final MemberService memberService;
	
    private final PasswordEncoder passwordEncoder;

	
	@Autowired
	public MypageController(MemberService memberService, PasswordEncoder passwordEncoder) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
	}
	
	@GetMapping("/mypage/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Principal principal, Model model) {
    	String userid = principal.getName();
    	Member member = memberService.findByUserid(userid);
    	model.addAttribute("member", member);
    	return "/security/profile";
    }
	
	@PutMapping("/mypage/update")
	public ResponseEntity<String> update(@RequestBody Member member) {
	    try {
	        memberService.updateMember(member);
	        return ResponseEntity.ok("회원정보가 수정되었습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정 중 오류가 발생했습니다.");
	    }
	}
	
	@GetMapping("/mypage/withdraw")
	public String withdrawPage() {
		return "/security/withdraw";
	}
	
	@PostMapping("/mypage/withdraw")
	public String withdraw(String password) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userid = authentication.getName();
		
		if(passwordEncoder.matches(password, memberService.getPassword(userid))) {
			memberService.deleteMember(userid);
			return "/";
		} else {
			return "redirect:/mypage/withdraw?error=true";
		}
	}




}

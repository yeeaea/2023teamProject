package com.pet.ques.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class MemberController {
	 @GetMapping("/auth/user/save")
	 public String userSave() {
		 return "layout/user/user-save";
	 }
}

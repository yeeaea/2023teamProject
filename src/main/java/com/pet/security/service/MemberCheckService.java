package com.pet.security.service;

public interface MemberCheckService {
	// 아이디
	boolean isUseridUnique(String userid);
	
	//닉네임
    boolean isNicknameUnique(String nickname);
    
    //이메일
    boolean isEmailUnique(String email);
    
    // 비밀번호
    boolean isPasswordValid(String password, String confirmPassword);
}

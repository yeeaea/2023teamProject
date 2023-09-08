package com.pet.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberJoinDto {

	@NotBlank(message = "아이디는 필수 입력 정보입니다.")
	@Pattern(regexp = "^[a-z0-9]{4,12}$",
			 message = "아이디는 4~12자로, 영문과 숫자를 포함해야 합니다.")
    private String userid;
	
	@NotBlank(message = "비밀번호는 필수 입력 정보입니다.")
	@Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,15}", 
			 message = "비밀번호는 8~15자로, 영문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    private String password;
	
	@NotBlank(message = "닉네임은 필수 입력 정보입니다.")
	@Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9]{2,10}$", 
			 message = "닉네임은 특수문자를 제외한 2~10자리여야 합니다.")
    private String nickname;
	
	@NotBlank(message = "이메일은 필수 입력 정보입니다.")
	@Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$", 
	         message = "이메일 형식이 올바르지 않습니다.")
    private String email;

   
}

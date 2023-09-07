package com.pet.security.domain;


import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor	
@Getter
@Setter
@Entity
public class Member {
	
	// 일련번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 아이디
    @Column(unique = true)
    private String userid;
    
    // 비밀번호
    private String password;
    
    // 닉네임
    @Column(unique = true)
    private String nickname;
    
    // 이메일
    @Column(unique = true)
    private String email;

    // 권한
    private String role;


    public static Member createUser(String userId, String pw, PasswordEncoder passwordEncoder, String nickname, String email) {
        return new Member(null, userId, passwordEncoder.encode(pw), nickname, email, "USER");
    }
}

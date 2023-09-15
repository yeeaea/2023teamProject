package com.pet.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pet.security.config.AdminAuthorize;
import com.pet.security.config.UserAuthorize;
import com.pet.security.dto.MemberJoinDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
    	if (error != null) {
            model.addAttribute("loginError", true);
        }
        return "/security/login.html";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, 	Authentication authentication) {
    	// 이전 요청을 가져오기 위한 RequestCache
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        
        // 이전 요청이 있고, 로그인한 사용자인 경우 이전 요청 페이지로 리다이렉트
        if (savedRequest != null && authentication != null && authentication.isAuthenticated()) {
            return "redirect:" + savedRequest.getRedirectUrl();
        } else {
            // 이전 요청이 없거나, 로그인하지 않은 경우 메인 페이지로 리다이렉트
            return "redirect:/";
        }
    }

    @GetMapping("/join")
    public String joinPage(Model model) {
    	model.addAttribute("dto", new MemberJoinDto());
        return "security/join.html";
    }

    @GetMapping("/setting/admin")
    @AdminAuthorize
    public String adminSettingPage() {
        return "/security/admin_setting.html";
    }

    @GetMapping("/setting/user")
    @UserAuthorize
    public String userSettingPage() {
        return "/security/user_setting.html";
    }
}
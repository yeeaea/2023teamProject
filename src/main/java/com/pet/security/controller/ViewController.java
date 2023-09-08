package com.pet.security.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pet.security.config.AdminAuthorize;
import com.pet.security.config.UserAuthorize;

@Controller
public class ViewController {

    @GetMapping("/login")
    public String loginPage() {
        return "/security/login.html";
    }

    @GetMapping("/join")
    public String joinPage() {
        return "/security/join.html";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("loginId", user.getUsername());
        model.addAttribute("loginRoles", user.getAuthorities());
        return "/security/dashboard.html";
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
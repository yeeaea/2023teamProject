package com.pet.security.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pet.free.domain.FreeBoard;
import com.pet.free.domain.FreeComment;
import com.pet.free.repository.FreeBoardRepository;
import com.pet.free.repository.FreeCommentRepository;
import com.pet.ques.domain.QuesBoard;
import com.pet.ques.domain.QuesComment;
import com.pet.ques.repository.QuesBoardRepository;
import com.pet.ques.repository.QuesCommentRepository;
import com.pet.security.domain.Member;
import com.pet.security.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/mypage")
public class MypageController {

	private final MemberService memberService;
	
    private final PasswordEncoder passwordEncoder;

    private final FreeBoardRepository freeBoardRepository;
    private final FreeCommentRepository freeCommentRepository;
    
    private final QuesBoardRepository quesBoardRepository;
    private final QuesCommentRepository quesCommentRepository;
	
	@Autowired
	public MypageController(MemberService memberService, PasswordEncoder passwordEncoder, 
							FreeBoardRepository freeBoardRepository, QuesBoardRepository quesBoardRepository,
							FreeCommentRepository freeCommentRepository, QuesCommentRepository quesCommentRepository) {
		this.memberService = memberService;
		this.passwordEncoder = passwordEncoder;
		this.freeBoardRepository = freeBoardRepository;
		this.freeCommentRepository = freeCommentRepository;
		this.quesBoardRepository = quesBoardRepository;
		this.quesCommentRepository = quesCommentRepository;
	}
	
	@GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Principal principal, Model model) {
    	String userid = principal.getName();
    	Member member = memberService.findByUserid(userid);
    	model.addAttribute("member", member);
    	return "/security/profile";
    }
	
	@PutMapping("/update")
	public ResponseEntity<String> update(@RequestBody Member member) {
	    try {
	        memberService.updateMember(member);
	        return ResponseEntity.ok("회원정보가 수정되었습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정 중 오류가 발생했습니다.");
	    }
	}
	
	@GetMapping("/withdraw")
	public String withdrawPage() {
		return "/security/withdraw";
	}
	
	@PostMapping("/withdraw")
	public String withdraw(
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
		// 현재 인증된 사용자의 정보 가져오기
		// 현재 로그인한 사용자의 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        
        // 사용자의 저장된 비밀번호 가져오기
        String storedPassword = memberService.getPasswordByUserid(userid);
        
        // 비밀번호 검증 및 탈퇴 로직
        if (passwordEncoder.matches(password, storedPassword)) {
            // 비밀번호 일치: 회원 탈퇴 및 로그아웃
        	memberService.deleteMember(userid);
            // Spring Security 로그아웃
            SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
            logoutHandler.logout(request, response, authentication);

            // 탈퇴 성공 메시지를 리다이렉트할 페이지에 전달
            redirectAttributes.addFlashAttribute("withdrawSuccess", true);

            return "redirect:/"; // 회원 탈퇴 성공 시 홈페이지로 리다이렉트
        } else {
            // 비밀번호 불일치: 실패 메시지를 리다이렉트할 페이지에 전달
            redirectAttributes.addFlashAttribute("withdrawFailure", true);

            return "redirect:/mypage/withdraw"; // 비밀번호 불일치 시 에러 메시지와 함께 탈퇴 페이지로 리다이렉트
        }
    }
	
	@GetMapping("/my-posts/freeboards")
	public String myPostsFree(Model model, Principal principal) {
		if(principal != null) {
			String userid = principal.getName();
			List<FreeBoard> myPosts = freeBoardRepository.findByUserid(userid);
			model.addAttribute("myPosts", myPosts);
		}
		
		return "/security/my-postsFree";
	}
	
	@GetMapping("/my-posts/questions")
	public String myPostsQues(Model model, Principal principal) {
		if(principal != null) {
			String userid = principal.getName();
			List<QuesBoard> myPosts = quesBoardRepository.findByUserid(userid);
			model.addAttribute("myPosts", myPosts);
		}
		
		return "/security/my-postsQues";
	}
	
	@GetMapping("/my-comments/freeboards")
    public String myCommentsFree(Model model, Principal principal) {
        if (principal != null) {
            String userid = principal.getName();
            List<FreeComment> myComments = freeCommentRepository.findByUserid(userid);
            model.addAttribute("myComments", myComments);
        }
        return "mypage/my-commentsFree";
    }
	
	@GetMapping("/my-comments/questions")
    public String myCommentsQues(Model model, Principal principal) {
        if (principal != null) {
            String userid = principal.getName();
            List<QuesComment> myComments = quesCommentRepository.findByUserid(userid);
            model.addAttribute("myComments", myComments);
        }
        return "mypage/my-commentsQues";
    }
	
}

$(document).ready(function() {
	// 초기에는 회원가입 버튼 비활성화
	$("#joinButton").prop("disabled", true);
	
	// 변수들을 선언하여 각 필드의 유효성 검사와 중복 검사 상태 추적
	let isUseridValid = false;
	let isPasswordValid = false;
	let isNicknameValid = false;
	let isEmailValid = false;
	
	// 아이디 입력 필드의 값이 변경될 때 마다
	$("#userid").on("input", function() {
		const userid = $(this).val();
		
		// 정규표현식 정의
		const useridPattern = /^[a-z0-9]{4,12}$/;
		
		// AJAX 요청을 통해 아이디 중복 확인 + 유효성 검사
		$.ajax({
			url: "api/valid/checkUserid",
			type: "GET",
			data: { userid: userid },
			success: function (response) {
				// 중복, 유효성 검사 여부에 따라 메시지 표시
				if (response === "사용 가능한 아이디입니다.") {
					// 중복이 아닌 경우
					if(!useridPattern.test(userid)) {
						// 정규 표현식과 일치하지 않는 경우
						$("#useridStatus").text(" *필수: 영문과 숫자를 포함한 4~12자").css({"color" : "red", "font-size" : "13px"});
						isUseridValid = false;
	    			} else {
	    				// 정규 표현식과 일치하는 경우
	    				$("#useridStatus").text(response).css({"color" : "green", "font-size" : "13px"});
	    				isUseridValid = true;
	    			}
				} enableJoinButtonIfValid();
			},
			error: function() {
				// 중복인 경우
				$("#useridStatus").text("이미 사용 중인 아이디입니다.").css({"color" : "red", "font-size" : "13px"});
				isUseridValid = false;
				enableJoinButtonIfValid();
			}
			
		});
	});
	
	// 비밀번호 유효성 검사
	$("#password").on("input", function() {
		const password = $("#password").val();
		
		// 비밀번호 정규표현식 정의
        const passwordPattern = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,15}$/;
        
        // 입력 값이 정규표현식과 일치하지 않는 경우
        if (!passwordPattern.test(password)) {
        	$("#passwordCheckStatus").text("*필수 : 영문, 숫자, 특수문자를 모두 포함한 8~15자").css({"color" : "red", "font-size" : "13px"});
        	isPasswordValid = false;
        	
        } else {
        	$("#passwordCheckStatus").text("사용 가능한 비밀번호입니다.").css({"color" : "green", "font-size" : "13px"});
        	isPasswordValid = true;
        }
        
        enableJoinButtonIfValid();
	})
	
	 // 비밀번호 입력 필드와 비밀번호 확인 입력 필드의 값이 변경될 때마다
    $("#confirmPassword").on("input", function () {
        const password = $("#password").val();
        const confirmPassword = $(this).val();
	
        // 비밀번호와 비밀번호 확인이 일치하는지 확인
        if (password === confirmPassword) {
			$("#passwordStatus").text("비밀번호 일치").css({"color" : "green", "font-size" : "13px"});
			isPasswordValid = true;
        } else {
            $("#passwordStatus").text("비밀번호 불일치").css({"color" : "red", "font-size" : "13px"});
            isPasswordValid = false;
        }
        enableJoinButtonIfValid();
    });
	
	
	// 닉네임 입력 필드의 값이 변경될 때마다
	$("#nickname").on("input", function() {
		const nickname = $(this).val();
		
		// 정규표현식 정의
		const nicknamePattern = /^[ㄱ-ㅎ가-힣a-z0-9]{2,10}$/;
		
		// AJAX  요청을 통해 닉네임 중복 확인 + 유효성 검사
		$.ajax({
			url: "/api/valid/checkNickname",
			type: "GET",
			data: { nickname: nickname },
			success: function (response) {
				// 중복, 유효성 검사 여부에 따라 메시지 표시
				if (response === "사용 가능한 닉네임입니다.") {
					// 중복이 아닌 경우
					if (!nicknamePattern.test(nickname)) {
						// 정규표현식과 일치하지 않는 경우
	    				$("#nicknameStatus").text("*필수 : 특수문자를 제외한 2~10자").css({"color" : "red", "font-size" : "13px"});
	    				isNicknameValid = false;
	    			} else {
	    				// 정규 표현식과 일치하는 경우
	    				$("#nicknameStatus").text(response).css({"color" : "green", "font-size" : "13px"});
	    				 isNicknameValid = true;
	    			}
				} 
				enableJoinButtonIfValid();
			},
			error: function() {
				// 중복인 경우
				$("#nicknameStatus").text("이미 사용 중인 닉네임입니다.").css({"color" : "red", "font-size" : "13px"});
				isNicknameValid = false;
				enableJoinButtonIfValid();
			}
		});
	});
	
	// 이메일 입력 필드의 값이 변경될 때마다
	$("#email").on("input", function() {
		const email = $(this).val();
		
		// 정규표현식 정의 
		const emailPattern = /^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/;

		// AJAX 요청을 통해 이메일 중복 확인 + 유효성 검사
		$.ajax({
			url: "/api/valid/checkEmail",
			type: "GET",
			data: { email: email },
			success: function (response) {
				// 중복, 유효성 검사 여부에 따라 메시지 표시
				if (response === "사용 가능한 이메일입니다.") {
					// 중복이 아닌 경우
					if (!emailPattern.test(email)) {
						// 정규표현식과 일치하지 않는 경우
	    	            $("#emailStatus").text("이메일 형식이 올바르지 않습니다.").css({"color" : "red", "font-size" : "13px"});
	    	            isEmailValid = false;
	    	        } else {
	    	            // 정규 표현식과 일치하는 경우
	    	        	$("#emailStatus").text(response).css({"color" : "green", "font-size" : "13px"});
	    	        	isEmailValid = true;
	    	        }
				}
				enableJoinButtonIfValid();
			},
			error: function () {
				// 중복인 경우
				$("#emailStatus").text("이미 사용 중인 이메일입니다.").css({"color" : "red", "font-size" : "13px"});
				isEmailValid = false;
				enableJoinButtonIfValid();
			}
		});
	});
	
	// 모든 검사가 성공하면 회원가입 버튼 활성화
	function enableJoinButtonIfValid() {
		if (isUseridValid && isPasswordValid && isNicknameValid && isEmailValid) {
            $("#joinButton").prop("disabled", false);
        } else {
            $("#joinButton").prop("disabled", true);
        }
	}
});


	const form = document.getElementById('join_form');

	form.addEventListener('submit', e => {
    e.preventDefault();

    const data = new FormData(form);
    const param = JSON.stringify(Object.fromEntries(data));

    fetch('/auth/join', {
        method: 'POST',
        body: param,
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(response => {
        if (response.status === 200) {
            // 회원가입 성공
            window.location.href = '/login';
            alert("회원가입 성공");
        } else if (response.status === 400) {
            // 서버에서 유효성 검사 오류가 발생한 경우
            response.text().then(errorMessage => {
                alert("회원가입 실패 : " + errorMessage);
            });
        } else {
            // 기타 오류 처리
            alert("오류 발생");
        }
    })
    .catch(error => console.log(error));
});
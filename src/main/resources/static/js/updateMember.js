$(document).ready(function() {
	// 비밀번호 유효성 검사
	$("#password").on("input", function() {
		const password = $("#password").val();

		// 비밀번호 정규표현식 정의
		const passwordPattern = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,15}$/;

		// 입력 값이 정규표현식과 일치하지 않는 경우
		if (!passwordPattern.test(password)) {
			$("#passwordCheckStatus").text("*필수 : 영문, 숫자, 특수문자를 모두 포함한 8~15자").css({ "color": "red", "font-size": "13px" });
			/*		$("#updateButton").prop("disabled", true);*/

		} else {
			$("#passwordCheckStatus").text("사용 가능한 비밀번호입니다.").css({ "color": "green", "font-size": "13px" });
			/*	$("#updateButton").prop("disabled", true);*/
		}


	})

	// 비밀번호 입력 필드와 비밀번호 확인 입력 필드의 값이 변경될 때마다
	$("#confirmPassword").on("input", function() {
		const password = $("#password").val();
		const confirmPassword = $(this).val();

		// 비밀번호와 비밀번호 확인이 일치하는지 확인
		if (password === confirmPassword) {
			$("#passwordStatus").text("비밀번호 일치").css({ "color": "green", "font-size": "13px" });
			/*	$("#updateButton").prop("disabled", false);*/

		} else {
			$("#passwordStatus").text("비밀번호 불일치").css({ "color": "red", "font-size": "13px" });
			/*	$("#updateButton").prop("disabled", true);*/

		}

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
			success: function(response) {
				// 중복, 유효성 검사 여부에 따라 메시지 표시
				if (response === "사용 가능한 닉네임입니다.") {
					// 중복이 아닌 경우
					if (!nicknamePattern.test(nickname)) {
						// 정규표현식과 일치하지 않는 경우
						$("#nicknameStatus").text("*필수 : 특수문자를 제외한 2~10자").css({ "color": "red", "font-size": "13px" });
						/*$("#updateButton").prop("disabled", true);*/

					} else {
						// 정규 표현식과 일치하는 경우
						$("#nicknameStatus").text(response).css({ "color": "green", "font-size": "13px" });
						/*	$("#updateButton").prop("disabled", false);*/

					}
				}

			},
			error: function() {
				// 중복인 경우
				$("#nicknameStatus").text("이미 사용 중인 닉네임입니다.").css({ "color": "red", "font-size": "13px" });
				/*		$("#updateButton").prop("disabled", true);*/

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
			success: function(response) {
				// 중복, 유효성 검사 여부에 따라 메시지 표시
				if (response === "사용 가능한 이메일입니다.") {
					// 중복이 아닌 경우
					if (!emailPattern.test(email)) {
						// 정규표현식과 일치하지 않는 경우
						$("#emailStatus").text("이메일 형식이 올바르지 않습니다.").css({ "color": "red", "font-size": "13px" });
						/*	$("#updateButton").prop("disabled", true);*/

					} else {
						// 정규 표현식과 일치하는 경우
						$("#emailStatus").text(response).css({ "color": "green", "font-size": "13px" });
						/*	$("#updateButton").prop("disabled", false);*/

					}
				}

			},
			error: function() {
				// 중복인 경우
				$("#emailStatus").text("이미 사용 중인 이메일입니다.").css({ "color": "red", "font-size": "13px" });
				/*	$("#updateButton").prop("disabled", true);*/
			}
		});
	});


	$("#update_form").submit(function(event) {
		event.preventDefault(); // 기본 제출 동작 막음

		const userid = $("#userid").val();
		const newPassword = $("#password").val();
		const confirmPassword = $("#confirmPassword").val();
		const nickname = $("#nickname").val();
		const email = $("#email").val();

		// 클라이언트 측에서 새로운 비밀번호 입력 여부 확인
		const isNewPasswordEmpty = newPassword.trim() === '';

		// 클라이언트 측에서 비밀번호 유효성 검사 (새로운 비밀번호가 입력된 경우에만)
		if (!isNewPasswordEmpty) {
			const passwordPattern = /^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\W)(?=\S+$).{8,15}$/;

			if (!passwordPattern.test(newPassword)) {
				alert("비밀번호가 유효하지 않습니다.");
				return; // 비밀번호 유효성 검사 실패 시 함수 종료
			}

			// 클라이언트 측에서 비밀번호 확인
			if (newPassword !== confirmPassword) {
				alert("비밀번호 확인이 일치하지 않습니다.");
				return; // 비밀번호 확인 실패 시 함수 종료
			}
		}

		// 클라이언트 측에서 닉네임 유효성 검사
		const nicknamePattern = /^[ㄱ-ㅎ가-힣a-z0-9]{2,10}$/;

		if (!nicknamePattern.test(nickname)) {
			alert("닉네임이 유효하지 않습니다.");
			return; // 닉네임 유효성 검사 실패 시 함수 종료
		}

		// 클라이언트 측에서 이메일 유효성 검사
		const emailPattern = /^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$/;

		if (!emailPattern.test(email)) {
			alert("이메일이 유효하지 않습니다.");
			return; // 이메일 유효성 검사 실패 시 함수 종료
		}

		// 서버로 회원 정보 업데이트 요청을 보냄
		$.ajax({
			url: "/mypage/update",
			method: "PUT",
			data: JSON.stringify({
				userid: userid,
				password: newPassword,
				confirmPassword: confirmPassword,
				nickname: nickname,
				email: email
			}),
			contentType: "application/json",
			success: function(response) {
				alert(response); // 성공 메시지 표시
			},
			error: function(error) {
				console.error("회원 정보 업데이트 실패: " + error);
			}
		});
	});

});

 function showContent(sectionId) {
      // 모든 섹션 비활성화
      const sections = document.querySelectorAll('.section');
      sections.forEach(section => {
        section.classList.remove('active');
      });

      // 선택한 섹션 활성화
      const selectedSection = document.getElementById(sectionId);
      selectedSection.classList.add('active');
    }

    
    // 현재 페이지 URL
        let currentPageUrl = window.location.href;

        // 모든 메뉴 항목에 대해 순회하며 현재 페이지와 비교
        let menuItems = document.querySelectorAll(".navbar-nav .nav-link");
        menuItems.forEach(function(item) {
            let menuItemUrl = item.getAttribute("href");
            if (currentPageUrl === menuItemUrl) {
                item.classList.add("active"); // 선택한 메뉴 항목에 'active' 클래스 추가
            }
        });
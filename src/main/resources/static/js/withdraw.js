document.addEventListener("DOMContentLoaded", function () {
    const withdrawButton = document.getElementById("withdrawButton");
    const passwordInput = document.getElementById("password");

    withdrawButton.addEventListener("click", function () {
        const password = passwordInput.value; // 비밀번호 입력값 가져오기

        // 회원 탈퇴 요청 보내기
        fetch("/mypage/withdraw", {
		    method: "POST",
		    headers: {
		        "Content-Type": "application/json" // Content-Type을 application/json으로 설정
		    },
		    body: JSON.stringify({ password: password })
		})
        .then(response => {
            if (response.status === 200) {
                return response.json(); // JSON 형태의 응답을 파싱
            } else {
                throw new Error("회원 탈퇴 요청 실패");
            }
        })
        .then(responseJson => {
            if (responseJson.result === "success") {
                alert("회원 탈퇴가 완료되었습니다.");

                // 회원 탈퇴가 성공한 후 메인 페이지로 이동
                window.location.href = "/";
            } else {
                alert("회원 탈퇴에 실패했습니다.");
            }
        })
        .catch(error => {
            console.error("회원 탈퇴 요청 실패: " + error.message);
        });
    });
});

document.addEventListener("DOMContentLoaded", function () {
    const withdrawButton = document.getElementById("withdrawButton");

    withdrawButton.addEventListener("click", function () {

        // 확인 알림창 표시
        const confirmation = confirm("정말로 회원 탈퇴하시겠습니까?");
        if (!confirmation) {
            return; // 취소 버튼을 누른 경우 동작 중지
        }
        
    });
});

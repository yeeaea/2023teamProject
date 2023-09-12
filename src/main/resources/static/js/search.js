document.addEventListener("DOMContentLoaded", function () {
    // AJAX 요청을 만들기 위한 준비
    const xhr = new XMLHttpRequest();
    const tableBody = document.getElementById("searchResults");

    // URL에서 쿼리 파라미터 추출
    const urlParams = new URLSearchParams(window.location.search);
    const selectedType = urlParams.get("type");
    const selectedSido = urlParams.get("sido");
    const selectedGugun = urlParams.get("gugun");
	
	// 검색 페이지 URL
	const url = `/api/places/search?type=${selectedType}&sido=${selectedSido}&gugun=${selectedGugun}`;

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4) {
            // 서버 응답이 완료된 경우
            if (xhr.status === 200) {
                const responseData = JSON.parse(xhr.responseText);

                // 테이블 내용 초기화
                tableBody.innerHTML = "";

                // 받아온 JSON 데이터를 테이블에 추가
                responseData.forEach(function (place) {
                    const row = tableBody.insertRow();
                    const nameCell = row.insertCell(0);
                    const addressCell = row.insertCell(1);
                    const telCell = row.insertCell(2);
                    const hoursCell = row.insertCell(3);

                    nameCell.innerText = place.name;
                    addressCell.innerText = place.addr;
                    telCell.innerText = place.tel;
                    hoursCell.innerText = place.time;
                });
            } else {
                // 서버에서 오류 응답을 받은 경우에 대한 처리
                console.error("서버에서 오류 응답을 받았습니다.");
            }
        }
    };

    // Get 요청을 서버로 보냄
    xhr.open("GET", url, true);
    xhr.send();
    
});

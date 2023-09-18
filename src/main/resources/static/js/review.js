document.addEventListener("DOMContentLoaded", function(){
	// 리뷰 개수 세는 부분
	const reviewList = document.getElementById("review-list");
	// 리뷰 개수 계산
	const reviewCount = reviewList.querySelectorAll("li").length;
	// 리뷰 개수를 포함하는 요소를 선택
	const reviewCountElement = document.getElementById("review-count");

	// 등록 버튼
	const submitButton = document.getElementById("submit-review");

	
	// 등록버튼을 클릭하면
	submitButton.addEventListener("click", () => {
		const reviewContent = document.getElementById("review-input").value;
		const placeNo = document.getElementById("placeNo").value;
		
		const score = parseInt(document.querySelector('input[type=radio][name=rating]:checked').value);
		
		var star = '';
		switch(score){
			case 1:
				star = '⭐'; break;
			case 2:
				star = '⭐⭐'; break;
			case 3:
				star = '⭐⭐⭐'; break;
			case 4:
				star = '⭐⭐⭐⭐'; break;
			case 5:
				star = '⭐⭐⭐⭐⭐'; break;
		}
		
		// 리뷰 데이터 생성
		const reviewData = {
			reviewContent: reviewContent,
			no: placeNo,
			reviewScore: score,
			star: star
		};
		
		if(reviewContent.trim() === ""){
			alert("리뷰 내용을 입력해주세요 !");
			return;
		}else{
			fetch("/api/reviews",{
				method: "POST",
				headers: {
					"Content-Type": "application/json"
				},
				body: JSON.stringify(reviewData)
			})
			.then(response => response.json())
			.then(data =>{
				location.reload();
			})
			.catch(error =>{
				console.error("Error: ", error);
			});
		}
	});

	// 모든 리뷰 요소를 선택
	const reviewScoreElements = document.querySelectorAll(".review-score");
	
	// 리뷰 점수를 저장할 배열 초기화
	const reviewScores = [];
	
	// 모든 리뷰의 점수를 추출하여 배열에 저장
	reviewScoreElements.forEach((element) => {
	    const score = parseFloat(element.textContent);
	    if (!isNaN(score)) {
	        reviewScores.push(score);
	    }
	});
	
	// 리뷰 점수의 합계 계산
	const totalScore = reviewScores.reduce((acc, score) => acc + score, 0);
	
	// 리뷰 평균 점수 계산
	const averageScore = totalScore / reviewScores.length;
	
	// 평균 점수를 원하는 형식으로 출력하려면 소수점 이하 자리수를 제한할 수 있습니다.
	const formattedAverageScore = averageScore.toFixed(1);
	
	if(reviewCount == 0){
		reviewCountElement.textContent = '등록된 리뷰가 없습니다.';
	}else{
		reviewCountElement.textContent = `★${formattedAverageScore} / 5 ㅤㅤ리뷰 ${reviewCount} 건`;
	}


	// 리뷰 삭제
	const commentList = document.getElementById("review-list");
	
	commentList.addEventListener("click", (event) => {
		const deleteButton = event.target;
		if(deleteButton.classList.contains("review-delete-btn")){
			const reviewItem = deleteButton.closest("li");
			
			if(reviewItem){
				const reviewNoElement = reviewItem.querySelector('.review-no');
				const reviewNo = parseInt(reviewNoElement.textContent);
				
				if(!isNaN(reviewNo)){
					// 사용자에게 삭제 여부를 묻는 confirm 대화 상자 표시
					Swal.fire({
						title: '리뷰 삭제',
						text: '정말로 이 리뷰를 삭제하시겠습니까?',
						icon: 'warning',
						showCancelButton: true,
						cancelButtonColor: '#d33',
						confirmButtonText: '확인',
           				cancelButtonText: '취소'
					}).then((result) => {
						
						if(result.isConfirmed){
							// 확인 버튼이 클릭된 경우 서버에 삭제 요청 보내고, 성공하면 삭제
							fetch(`/api/reviews/${reviewNo}`, {
								method: "DELETE"
							})
							.then(response => {
								if(response.status === 200){
									reviewItem.remove();
									location.reload();
								}else{
									console.log("리뷰 삭제 실패");
								}
							})
							.catch(error => {
								console.error("Error:", error);
							});
						}
					});
				}
			}
		}
	});
	
	function show () {
	  document.querySelector(".background").className = "background show";
	}
	
	function close () { 
	  document.querySelector(".background").className = "background";
	}
	
	document.querySelector("#show").addEventListener('click', show);
	document.querySelector("#close").addEventListener('click', close);

});
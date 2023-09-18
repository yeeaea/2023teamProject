document.addEventListener("DOMContentLoaded", function(){
	// 리뷰 개수 세는 부분
	const reviewList = document.getElementById("review-list");
	// 리뷰 개수 계산
	const reviewCount = reviewList.querySelectorAll("li").length;
	// 리뷰 개수를 포함하는 요소를 선택
	const reviewCountElement = document.getElementById("review-count");
	
	if(reviewCount == 0 ){
		reviewCountElement.textContent = '등록된 리뷰가 없습니다.';
	}else{
		reviewCountElement.textContent = `★4.5 / 5 리뷰 ${reviewCount} 건`;
	}
	
	const submitButton = document.getElementById("submit-review");
	
	submitButton.addEventListener("click", () =>{
		const placeNo = document.getElementById("placeNo").value;
		
		(async () => {
			const {value: content, dismiss: isDismissed } = await Swal.fire({
				title: '리뷰 작성하기',
				input: 'textarea',
				inputPlaceholder: '200글자 내로 입력..',
				inputAttributes: {
					style: ' height: 200px; font-size: 16px;',
					maxlength: 200,  // 최대 길이를 5로 제한
					wrap: 'soft'
				},
				showCloseButton : true,
			});
			
			if(!isDismissed){ // 사용자가 닫기 버튼을 누르지 않았을 때
				if(content){
					if(content.length >= 200){
						Swal.fire('리뷰는 200글자 이상 입력할 수 없습니다.');
					} else if(content.length < 5){
						Swal.fire('리뷰는 최소 5글자 이상 입력해야 합니다.');
					} else{
						const commentData = {
							reviewContent: content,
							no: placeNo
						};
						
						fetch("/api/reviews", {
							method: "POST",
							headers: {
								"Content-Type": "application/json"
							},
							body: JSON.stringify(commentData)
						})
						.then(response => response.json())
						.then(data => {
							location.reload();
						})
						.catch(error => {
							console.error("Error: ", error);
						});
					}
				} else{
					Swal.fire('리뷰를 입력해주세요!');
				}
			}
		})();
	});
	
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
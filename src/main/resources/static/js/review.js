document.addEventListener("DOMContentLoaded", function() {
	const commentForm = document.getElementById("comment-form");
	const submitButton = document.getElementById("submit-review");
	const reviewList = document.getElementById("review-list");

	submitButton.addEventListener("click", () => {

		const placeNo = document.getElementById("placeNo").value;

		(async () => {
			const { value: content } = await Swal.fire({
				title: '리뷰 작성하기',
				input: 'text',
				inputPlaceholder: '리뷰 입력..'
			})

			// 이후 처리되는 내용.
			if (content) {
				const commentData = {
					reviewContent: content,
					no: placeNo
				}
				if(content.trim()===''){
					Swal.fire('리뷰를 입력해주세요!');
			} else {
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
						console.error("Error:", error);
					});
				}
			}
		})()
	});
});

/*		
		
		console.log("클릭함");
		// 리뷰 내용과 질문 번호 가져오기
		const reviewContent = document.getElementById("reviewContent").value;
		const placeNo = document.getElementById("placeNo").value;
		
		// 리뷰 데이터 생성
		const commentData = {
			reviewContent: reviewContent,
			no: placeNo
		};
		console.log(JSON.stringify(commentData))
		
		if(reviewContent.trim() === ""){
			alert("내용을 입력해주세요 !");
		} else{
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
				console.error("Error:", error);
			});
			
		}

	})
})
*/
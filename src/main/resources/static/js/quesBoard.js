// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
	deleteButton.addEventListener('click', event => {
		const isConfirmed = confirm('게시글을 삭제하시겠습니까?');
		if (isConfirmed) {
			let quesNo = document.getElementById('question-quesNo').value;

			fetch(`/api/questions/${quesNo}`, {
				method: 'DELETE'
			})
				.then(() => {
					alert('삭제가 완료되었습니다.');
					location.replace('/questions');
				});
		}
	});
}

// 이미지 삭제하기
document.addEventListener("DOMContentLoaded", function() {
	// 이미지 삭제 버튼 클릭 시 이벤트 핸들러 추가
	document.getElementById("deleteImg").addEventListener("click", function() {
		var confirmDelete = confirm("이미지를 삭제하시겠습니까?");
		if (confirmDelete) {
			// 이미지 업로드 필드의 값을 지우기
			document.getElementById("imageUpload").value = "";
		}
	});
});

// 수정 기능
const modifyButton = document.getElementById("modify-btn");

if (modifyButton) {
	modifyButton.addEventListener("click", event => {
		event.preventDefault();

		let quesNo = document.getElementById('question-quesNo').value;
		let title = document.getElementById("quesTitle").value;
		let content = document.getElementById("quesContent").value;

		const formData = new FormData();
		formData.append("quesTitle", document.getElementById("quesTitle").value);
		formData.append("quesContent", document.getElementById("quesContent").value);
		formData.append("file", document.querySelector('input[type="file"]').files[0]);

		if (title == "" && content == "") {
			alert("제목과 내용을 입력해주세요!")
		} else if (content == "") {
			alert("내용을 입력해주세요!");
		} else if (title == "") {
			alert("제목을 입력해주세요!")
		} else {
			// URL에 실제 quesNo 값을 대체하여 요청을 보냅니다.
			fetch(`/api/questions/${quesNo}`, {
				method: "PUT",
				body: formData,
			})
				.then((response) => {
					if (response.status === 200) {
						alert("수정을 완료했습니다.");
						location.replace(`/questions/${quesNo}`);
					} else {
						alert("수정을 실패했습니다.");
					}
				})
				.catch((error) => {
					console.error("Error:", error);
					alert("수정을 실패했습니다.");
				});
		}
	});

}

// 생성 기능
const createButton = document.getElementById("create-btn");
if (createButton) {
	createButton.addEventListener("click", (event) => {
		event.preventDefault();

		const formData = new FormData();
		formData.append("quesTitle", document.getElementById("quesTitle").value);
		formData.append("quesContent", document.getElementById("quesContent").value);
		formData.append("file", document.querySelector('input[type="file"]').files[0]);

		let title = document.getElementById("quesTitle").value;
		let content = document.getElementById("quesContent").value;

		if (title == "" && content == "") {
			alert("제목과 내용을 입력해주세요!")
		} else if (content == "") {
			alert("내용을 입력해주세요!");
		} else if (title == "") {
			alert("제목을 입력해주세요!")
		} else {
			fetch("/api/questions", {
				method: "POST",
				body: formData,
			}).then((response) => {
				if (response.status === 201) {
					alert("등록이 완료되었습니다.");
					location.replace("/questions");
				} else {
					alert("등록을 실패했습니다.");
				}
			})
				.catch((error) => {
					console.error("Error: ", error);
					alert("등록을 실패했습니다.");
				});
		}
	});
}

// 글 목록으로 가는 기능
const listButton = document.getElementById("list-btn");

if (listButton) {
	listButton.addEventListener('click', event => {
		location.replace("/questions");
	});
}

// 조회수 정렬
function changeSort(sortBy) {
	// 현재 페이지 URL 가져오기
	const currentUrl = new URL(window.location.href);

	// sortBy 파라미터 설정
	currentUrl.searchParams.set('sortBy', sortBy);

	// sort 파라미터 제거 (기존 버전의 파라미터를 제거함)
	currentUrl.searchParams.delete('sort');

	// 현재 페이지 번호 가져오기
	const currentPage = currentUrl.searchParams.get('page');

	// 페이지 번호가 있는 경우 페이지 번호도 설정
	if (currentPage !== null) {
		currentUrl.searchParams.set('page', currentPage);
	}

	// 새로운 URL로 이동
	window.location.href = currentUrl.toString();
}


// 댓글 등록
const commentForm = document.getElementById("comment-form");
const submitButton = document.getElementById("submit-comment");
const commentList = document.getElementById("comment-list");

submitButton.addEventListener("click", () => {
	// 댓글 내용과 질문 번호 가져오기
	const quesCmtContent = document.getElementById("quesCmtContent").value;
	const quesNo = document.getElementById("quesNo").value;

	// 댓글 데이터 생성
	const commentData = {
		quesCmtContent: quesCmtContent,
		quesNo: quesNo
	};

	if (quesCmtContent.trim() === "") {
		alert("댓글 내용을 입력해주세요!");
		return;

	} else {
		// 서버로 댓글 데이터 전송
		fetch("/api/quescomments", {
			method: "POST",
			headers: {
				"Content-Type": "application/json"
			},
			body: JSON.stringify(commentData)
		})
			.then(response => response.json())
			.then(data => {
				// 페이지 새로 고침
				location.reload();
			})
			.catch(error => {
				console.error("Error:", error);
			});
	}
});

// 댓글 삭제 버튼 이벤트 리스너
commentList.addEventListener('click', (event) => {
	const deleteButton = event.target;
	if (deleteButton.classList.contains('cmt-delete-btn')) {
		// 삭제 버튼의 부모 요소인 댓글 요소를 찾습니다.
		const commentItem = deleteButton.closest('li'); // 또는 댓글 요소를 감싸는 부모 요소의 셀렉터를 사용

		if (commentItem) { // 댓글 요소를 찾은 경우에만 실행
			const confirmation = confirm('댓글을 삭제하시겠습니까?');

			if (confirmation) {
				// 댓글 요소에서 댓글 번호를 가져오는 방법 (이 부분은 실제 DOM 구조에 따라 다를 수 있음)
				const quesCmtNoElement = commentItem.querySelector('.comment-no');
				const quesCmtNo = parseInt(quesCmtNoElement.textContent);

				if (!isNaN(quesCmtNo)) { // quesCmtNo가 숫자인 경우에만 실행
					fetch(`/api/quescomments/${quesCmtNo}`, {
						method: "DELETE"
					})
						.then(response => {
							if (response.status === 200) {
								// 삭제 요청이 성공하면 화면에서 해당 댓글을 제거합니다.
								commentItem.remove();
								console.log(`${quesCmtNo}번 댓글 삭제`);
							} else {
								console.error("댓글 삭제 실패");
							}
						})
						.catch(error => {
							console.error("Error:", error);
						});
				}
			}
		}
	}
});

document.addEventListener("DOMContentLoaded", function() {
	let editingCommentNo = null;

	// 공통 함수: 수정 폼을 보이거나 숨기고, 내용을 설정하는 함수
	function toggleEditFormAndContent(commentItem, updatedContent) {
		const commentContent = commentItem.querySelector('.comment-content');
		const editForm = commentItem.querySelector('.edit-comment-form');
		const editTextarea = editForm.querySelector('.edit-comment-textarea');

		editTextarea.value = commentContent.textContent;
		updatedContent.value = commentContent.textContent;

		commentContent.style.display = 'none';
		editForm.style.display = 'block';
	}

	// "수정" 버튼 클릭 시
	document.querySelectorAll('.edit-comment-btn').forEach(button => {
		button.addEventListener('click', () => {
			const commentItem = button.closest('.list-group-item');
			const editTextarea = commentItem.querySelector('.edit-comment-textarea');

			toggleEditFormAndContent(commentItem, editTextarea);

			// 클릭된 "수정" 버튼의 부모 요소인 댓글 요소에서 댓글 번호 추출
			const quesCmtNoElement = commentItem.querySelector('.comment-no');
			const quesCmtNo = parseInt(quesCmtNoElement.textContent);

			if (!isNaN(quesCmtNo)) { // 파싱한 결과가 NaN이 아닌 경우에만 사용
				editingCommentNo = quesCmtNo;
			}
		});
	});

	// "저장" 및 "취소" 버튼 클릭 시
	document.querySelectorAll('.save-comment-btn, .cancel-comment-btn').forEach(button => {
		button.addEventListener('click', () => {
			const editForm = button.closest('.edit-comment-form');
			const editTextarea = editForm.querySelector('.edit-comment-textarea');
			const commentContent = editForm.parentElement.querySelector('.comment-content');
			const commentDate = editForm.parentElement.querySelector('.comment-date');

			if (button.classList.contains('save-comment-btn') && editingCommentNo !== null) {
				const updatedContent = editTextarea.value;

				// 댓글 내용이 없으면 alert
				if (updatedContent.trim() === "") {
					alert("수정할 댓글 내용을 입력해주세요!");
					return;
				}

				// 서버에 업데이트 요청 보내기
				fetch(`/api/quescomments/${editingCommentNo}`, { // 수정 중인 댓글 번호 사용
					method: "PUT",
					headers: {
						"Content-Type": "application/json"
					},
					body: JSON.stringify({ quesCmtContent: updatedContent })
				})
					.then(response => {
						if (response.status === 200) {
							// 성공적으로 업데이트되면 화면에 수정된 내용과 시간 반영
							commentContent.textContent = updatedContent;
							const now = new Date();
							commentDate.textContent = now.toLocaleString();
							alert("수정을 완료했습니다.");
							location.reload();
						} else {
							console.error("댓글 수정 실패");
						}
					})
					.catch(error => {
						console.error("Error:", error);
					});
			}

			// 수정 폼을 숨기고 댓글 내용을 다시 보이도록 설정
			editForm.style.display = 'none';
			commentContent.style.display = 'block';

			// 수정할 댓글 번호 초기화
			editingCommentNo = null;
		});
	});
});

// 댓글 길이 카운팅
function countingLength(inputElementId, counterElementId) {
	let inputElement = document.getElementById(inputElementId);
	let counter = document.getElementById(counterElementId);

	if (inputElement.value.length > 200) {
		alert('최대 200자까지 입력 가능합니다.');
		inputElement.value = inputElement.value.substring(0, 200);
		inputElement.focus();
	}

	counter.innerText = inputElement.value.length + '/200자';
}

// 호출할 때 함수 이름을 다르게 지정하여 각각의 입력 필드에 적용
let quesCmtContent = document.getElementById('quesCmtContent');
let counterCmt = document.getElementById('counterCmt');
quesCmtContent.addEventListener('input', function() {
	countingLength('quesCmtContent', 'counterCmt');
});

let editquesCmtContent = document.getElementById('editquesCmtContent');
let counterEdit = document.getElementById('counterEdit');
editquesCmtContent.addEventListener('input', function() {
	countingLength('editquesCmtContent', 'counterEdit');
});
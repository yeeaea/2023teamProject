// 삭제 기능
const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
	deleteButton.addEventListener('click', event => {
		let quesNo = document.getElementById('question-quesNo').value;
		fetch(`/api/questions/${quesNo}`, {
			method: 'DELETE'
		})
			.then(() => {
				alert('삭제가 완료되었습니다.');
				location.replace('/questions');
			});
	});
}

// 수정 기능
const modifyButton = document.getElementById("modify-btn");

if (modifyButton) {
  modifyButton.addEventListener("click", event => {
    event.preventDefault(); // 기본 이벤트 방지

    // 수정할 글의 ID (quesNo)를 가져옵니다.
    let quesNo = document.getElementById('question-quesNo').value;

    const formData = new FormData();
    formData.append("quesTitle", document.getElementById("quesTitle").value);
    formData.append("quesContent", document.getElementById("quesContent").value);
    formData.append("file", document.querySelector('input[type="file"]').files[0]);

    // URL에 실제 quesNo 값을 대체하여 요청을 보냅니다.
    fetch(`/api/questions/${quesNo}`, {
      method: "PUT",
      body: formData,
    })
      .then((response) => {
        if (response.status === 200) {
          alert("수정 완료");
          location.replace(`/questions/${quesNo}`);
        } else {
          alert("수정 실패");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("수정 실패");
      });
  });
}


// 수정 기능2
const modifyButton2 = document.getElementById("modify-btn2");

if (modifyButton2) {
	modifyButton2.addEventListener("click", event => {
		let params = new URLSearchParams(location.search);
		let quesNo = params.get("quesNo");

		let title = document.getElementById("quesTitle").value;
		let content = document.getElementById("quesContent").value;

		if (title == "" && content == "") {
			alert("제목과 내용을 입력해주세요!")
		} else if (content == "") {
			alert("내용을 입력해주세요!");
		} else if (title == "") {
			alert("제목을 입력해주세요!")
		} else {
			fetch(`/api/questions/${quesNo}`, {
				method: 'PUT',
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					quesTitle: document.getElementById("quesTitle").value,
					quesContent: document.getElementById("quesContent").value,
				})
			})
				.then(() => {
					alert("수정이 완료되었습니다.")
					location.replace(`/questions/${quesNo}`);
				});
		}
	});
}

// 생성 기능
const createButton = document.getElementById("create-btn");

if (createButton) {
  createButton.addEventListener("click", (event) => {
    event.preventDefault(); // 기본 이벤트 방지

console.log("createButton 클릭 이벤트 발생");

    const formData = new FormData();
    formData.append("quesTitle", document.getElementById("quesTitle").value);
    formData.append("quesContent", document.getElementById("quesContent").value);
    formData.append("file", document.querySelector('input[type="file"]').files[0]);

    fetch("/api/questions", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (response.status === 201) {
          alert("등록 완료");
          location.replace("/questions");
        } else {
          alert("등록 실패");
        }
      })
      .catch((error) => {
        console.error("Error:", error);
        alert("등록 실패");
      });
  });
} 

// 등록 기능2
const createButton2 = document.getElementById("create-btn2");
if (createButton2) {
	createButton2.addEventListener("click", (event) => {
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
				headers: {
					"Content-Type": "application/json",
				},
				body: JSON.stringify({
					quesTitle: document.getElementById("quesTitle").value,
					quesContent: document.getElementById("quesContent").value,
				}),
			}).then(() => {
				alert("등록이 완료되었습니다.");
				location.replace("/questions");
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
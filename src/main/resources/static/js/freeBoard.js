const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
	deleteButton.addEventListener('click', event => {
		let freeNo = document.getElementById('freeBoard-freeNo').value;

		fetch(`/api/freeboards/${freeNo}`, {
			method: 'DELETE'
		})
			.then(() => {
				alert('삭제가 완료되었습니다.');
				location.replace('/freeboards');
			});
	});
}

const modifyButton = document.getElementById('modify-btn');

if (modifyButton) {
	modifyButton.addEventListener('click', event => {
		let params = new URLSearchParams(location.search);
		let freeNo = params.get('freeNo');

		fetch(`/api/freeboards/${freeNo}`, {
			method: 'PUT',
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				freeTitle: document.getElementById('freeTitle').value,
				freeContent: document.getElementById('freeContent').value
			})
		})
			.then(() => {
				alert('수정이 완료되었습니다.');
				location.replace(`/freeboards/${freeNo}`);
			});
	});
}

// 수정 기능2
const modifyButton2 = document.getElementById('modify-btn2');

if(modifyButton2) {
	modifyButton2.addEventListener('click', event => {
		let params = new URLSearchParams(location.search);
		let freeNo = params.get("freeNo");

		let title = document.getElementById("freeTitle").value;
		let content = document.getElementById("freeContent").value;

		if (title == "" && content == "") {
			alert("제목과 내용을 입력해주세요!")
		} else if (content == "") {
			alert("내용을 입력해주세요!");
		} else if (title == "") {
			alert("제목을 입력해주세요!");
		} else {

		fetch(`/api/freeboards/${freeNo}`, {
			method: 'PUT',
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				freeTitle: document.getElementById('freeTitle').value,
				freeContent: document.getElementById('freeContent').value
			})
		})
			.then(() => {
				alert('수정이 완료되었습니다.');
				location.replace(`/freeboards/${freeNo}`);
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
    formData.append("freeTitle", document.getElementById("freeTitle").value);
    formData.append("freeContent", document.getElementById("freeContent").value);
    formData.append("file", document.querySelector('input[type="file"]').files[0]);

    fetch("/api/freeboard", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (response.status === 201) {
          alert("등록 완료");
          location.replace("/freeboards");
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
const createButton2 = document.getElementById('create-btn2');

if (createButton2) {
	createButton2.addEventListener('click', event => {
		let title = document.getElementById("freeTitle").value;
		let content = document.getElementById("freeContent").value;

		if (title == "" && content == "") {
			alert("제목과 내용을 입력해주세요!")
		} else if (content == "") {
			alert("내용을 입력해주세요!");
		} else if (title == "") {
			alert("제목을 입력해주세요!");
		} else {
		fetch('/api/freeboard', {
			method: 'POST',
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify({
				freeTitle: document.getElementById('freeTitle').value,
				freeContent: document.getElementById('freeContent').value
			})
		})
			.then(() => {
				alert('등록이 완료되었습니다.');
				location.replace('/freeboards');
			});
		}
	});
}

// 글 목록으로 가는 기능
const listButton = document.getElementById("list-btn");

if (listButton) {
	listButton.addEventListener('click', event => {
		location.replace("/freeboards");
	});
}
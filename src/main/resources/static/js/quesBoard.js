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

if(modifyButton){
	modifyButton.addEventListener("click", event => {
		let params = new URLSearchParams(location.search);
		let quesNo = params.get("quesNo");
		
		fetch(`/api/questions/${quesNo}`, {
			method:'PUT',
			headers:{
				"Content-Type" : "application/json",
			},
			body:JSON.stringify({
				quesTitle:document.getElementById("quesTitle").value,
				quesContent:document.getElementById("quesContent").value,
			})
		})
		.then(()=>{
			alert("수정 완료")
			location.replace(`/questions/${quesNo}`);
		});
	});
}

// 생성 기능
const createButton = document.getElementById("create-btn");
if(createButton){
	createButton.addEventListener("click", (event) => {
		fetch("/api/questions", {
			method: "POST",
			headers: {
				"Content-Type" : "application/json",
			},
			body: JSON.stringify({
				quesTitle: document.getElementById("quesTitle").value,
				quesContent: document.getElementById("quesContent").value,
			}),
		}).then(() => {
			alert("등록 완료");
			location.replace("/questions");
		});
	});
}

// 글 목록으로 가는 기능
const listButton = document.getElementById("list-btn");

if(listButton){
   listButton.addEventListener('click', event =>{
      location.replace("/questions");
   });
}
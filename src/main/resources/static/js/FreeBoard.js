const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
	deleteButton.addEventListener('click', event => {
		let freeNo = document.getElementById('freeBoard-freeNo').value;
		
		fetch(`/freeboards/${freeNo}`,{
			method: 'DELETE'
		})
		.then(()=> {
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
		
		fetch(`/freeboards/${freeNo}`, {
			method: 'PUT',
			headers: {
				"Content-Type" : "application/json",
			},
			body: JSON.stringify({
				freeTitle: document.getElementById('freeTitle').value,
				freeContent: document.getElementById('freeContent').value
			})
		})
		.then(()=> {
			alert('수정이 완료되었습니다.');
			location.replace(`/freeboards/${freeNo}`);
		});
	});
}

// 등록버튼이 눌러졌으면 그 등록버튼의 이벤트 핸들러 등록
const createButton = document.getElementById('create-btn')

// createButton이 있으면 이벤트 등록
if (createButton) {
    createButton.addEventListener('click', event => {
        fetch('/freeBoards', {
            method: 'POST',
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                title: document.getElementById('freeTitle').value,
                content: document.getElementById('freeContent').value
            })
        })
        .then(() => {
            alert('등록 완료되었습니다.');
            location.replace('/freeBoards');
        });
    });
}
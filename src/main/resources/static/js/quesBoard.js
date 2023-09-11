const deleteButton = document.getElementById('delete-btn');

if (deleteButton) {
  deleteButton.addEventListener('click', event => {
    // 사용자에게 확인 메시지를 표시
    const confirmDelete = confirm('삭제하시겠습니까?');

    // 확인 버튼이 눌렸을 경우만 삭제 진행
    if (confirmDelete) {
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

// 수정 기능
const modifyButton = document.getElementById("modify-btn");

if (modifyButton) {
   modifyButton.addEventListener("click", event => {
   event.preventDefault(); // 기본 이벤트 방지      
      
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
      }
  });
}

// 생성 기능
const createButton = document.getElementById("create-btn");

if (createButton) {
  createButton.addEventListener("click", (event) => {
    event.preventDefault(); // 기본 이벤트 방지

   // console.log("createButton 클릭 이벤트 발생");
   
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

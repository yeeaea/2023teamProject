// 글 작성하기에서 내용 2000자 못넘게
function countingLength(inputElementId, counterElementId) {
	let quesInputElement = document.getElementById(inputElementId);
	let quesCounter = document.getElementById(counterElementId);

	if (quesInputElement.value.length > 1000) {
		alert('댓글을 1000자 이하로 입력해 주세요.');
		quesInputElement.value = quesInputElement.value.substring(0, 1000);
		quesInputElement.focus();
	}

	quesCounter.innerText = quesInputElement.value.length + '/1000자';
}



$(document).ready(function() {
	$('#freeContent').keyup(function(){
	  var content = $(this).val();
	  $('.count span').html(content.length);
	  if (content.length > 1000){
	    alert("최대 1000자까지 입력 가능합니다.");
	    $(this).val(content.substring(0, 1000)); // 2000자까지만 입력값으로 설정
	    $('.count span').html(1000);
	  }
	});
});
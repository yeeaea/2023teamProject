// 글 작성하기에서 내용 2000자 못넘게
function countingLength(inputElementId, counterElementId) {
	let wordInputElement = document.getElementById(inputElementId);
	let wordCounter = document.getElementById(counterElementId);

	if (wordInputElement.value.length > 1000) {
		alert('최대 1000자까지 입력 가능합니다.');
		wordInputElement.value = wordInputElement.value.substring(0, 1000);
		wordInputElement.focus();
	}

	wordCounter.innerText = wordInputElement.value.length + '/1000자';
}
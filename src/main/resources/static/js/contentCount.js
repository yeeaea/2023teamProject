// 글 작성하기에서 내용 2000자 못넘게
$('#quesContent').keyup(function (e) {
  
    if($(this).val().length > 1000){
		alert("최대 1000자까지 입력 가능합니다.");
		$(this).val($(this).val().substring(0, 1000));
	}
    $('#cnt').html($(this).val().length + "자");
});

$('#freeContent').keyup(function (e) {
  
    if($(this).val().length > 1000){
		alert("최대 1000자까지 입력 가능합니다.");
		$(this).val($(this).val().substring(0, 1000));
	}
    $('#cnt').html($(this).val().length + "자");
});
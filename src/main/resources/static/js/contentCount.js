// 글 작성하기에서 내용 2000자 못넘게
$(document).ready(function() {
	$('#quesContent').keyup(function(){
	  var content = $(this).val();
	  $('.count span').html(content.length);
	  if (content.length > 1000){
	    alert("최대 1000자까지 입력 가능합니다.");
	    $(this).val(content.substring(0, 1000)); // 2000자까지만 입력값으로 설정
	    $('.count span').html(1000);
	  }
	});
});

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
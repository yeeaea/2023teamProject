// 글 작성하기에서 내용 2000자 못넘게
$('#quesContent').on('keydown', function() {
    var content = $(this).val();
    if (content.length >= 1000) {
        alert("최대 1000자까지 입력 가능합니다.");
        $(this).val(content.substring(0, 1000));
    }
    $('#cnt').html(content.length + "자");
});

$('#freeContent').on('keydown', function() {
    var content = $(this).val();
    if (content.length >= 1000) {
        alert("최대 1000자까지 입력 가능합니다.");
        $(this).val(content.substring(0, 1000));
    }
    $('#cnt').html(content.length + "자");
});
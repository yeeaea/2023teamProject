//header footer load page
document.addEventListener("DOMContentLoaded", function () {
	let headerContainer = document.getElementById("header-container");

    if (headerContainer) {
        fetch('/html/header_nav.html')
            .then(response => response.text())
            .then(data => {
                headerContainer.innerHTML = data;
            });
   	}
});

//footer load page
document.addEventListener("DOMContentLoaded", function () {
	let headerContainer = document.getElementById("footer-container");

    if (headerContainer) {
        fetch('/html/footer.html')
            .then(response => response.text())
            .then(data => {
                headerContainer.innerHTML = data;
            });
   	}
});

// SelectBox
document.addEventListener("DOMContentLoaded", function () {
	const typeSelect = document.getElementById("type");
	const sidoSelect = document.getElementById("sido");
	const gugunSelect = document.getElementById("gugun");
	const searchBtn = document.getElementById("search-btn");
	
	// 업체 종류 로드
	fetch("/api/places/types")
		.then(response => response.json())
		.then(types => {
			populateSelect(typeSelect, types);
  		});
	
	// 업체 종류 선택 시 시/도 로드
	typeSelect.addEventListener("change", function () {
		const selectedType = typeSelect.value;
		fetch(`/api/places/sidos?type=${selectedType}`)
			.then(response => response.json())
			.then(sidos => {
				populateSelect(sidoSelect, sidos);
			});
	});
	
	// 시/도 선택 시 군/구 로드
	sidoSelect.addEventListener("change", function () {
	    const selectedType = typeSelect.value;
	    const selectedSido = sidoSelect.value;
	
	    // 두 번째 셀렉트박스 데이터를 가져온 후에 세 번째 셀렉트박스 데이터를 가져옴
	    fetch(`/api/places/guguns?type=${selectedType}&sido=${selectedSido}`)
	        .then(response => response.json())
	        .then(guguns => {
	            populateSelect(gugunSelect, guguns);
	        });
	});

	
	// 검색 버튼 클릭 시 검색 페이지로 이동
	searchBtn.addEventListener("click", function () {
		const selectedType = typeSelect.value;
		const selectedSido = sidoSelect.value;
		const selectedGugun = gugunSelect.value;
		
		// 검색 페이지로 이동하는 URL
		const searchURL = `/search?type=${selectedType}&sido=${selectedSido}&gugun=${selectedGugun}`;
		window.location.href = searchURL
	});
	
	// 셀렉트 박스에 옵션을 추가하는 함수
	function populateSelect(selectElement, options) {
	    // 셀렉트 박스 초기화
	    selectElement.innerHTML = "";
	
	    // 기본 옵션 추가
	    const defaultOption = document.createElement("option");
	    defaultOption.value = "";
	    defaultOption.textContent = "선택하세요";
	    defaultOption.disabled = true;
	    defaultOption.selected = true;
	    selectElement.appendChild(defaultOption);
	
	    // 옵션들 추가
	    options.forEach(option => {
	        const optionElement = document.createElement("option");
	        optionElement.value = option;
	        optionElement.textContent = option;
	        selectElement.appendChild(optionElement);
	    });
	}
});


(function ($) {
    "use strict";
    
    // Initiate the wowjs
    new WOW().init();
    
    // Sticky Navbar
    $(window).scroll(function () {
        if ($(this).scrollTop() > 45) {
            $('.nav-bar').addClass('sticky-top');
        } else {
            $('.nav-bar').removeClass('sticky-top');
        }
    });
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 300) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Header carousel
    $(".header-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        items: 1,
        dots: true,
        loop: true,
        nav : true,
        navText : [
            '<i class="bi bi-chevron-left"></i>',
            '<i class="bi bi-chevron-right"></i>'
        ]
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1000,
        margin: 24,
        dots: false,
        loop: true,
        nav : true,
        navText : [
            '<i class="bi bi-arrow-left"></i>',
            '<i class="bi bi-arrow-right"></i>'
        ],
        responsive: {
            0:{
                items:1
            },
            992:{
                items:2
            }
        }
    });
    
})(jQuery);
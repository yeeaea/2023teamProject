document.addEventListener("DOMContentLoaded", function() {
   // AJAX 요청을 만들기 위한 준비
   const xhr = new XMLHttpRequest();
   const tableBody = document.getElementById("searchResults");

   // URL에서 쿼리 파라미터 추출
   const urlParams = new URLSearchParams(window.location.search);
   const selectedType = urlParams.get("type");
   const selectedSido = urlParams.get("sido");
   const selectedGugun = urlParams.get("gugun");
   const lat = [];
   const lon = [];

   var mapContainer = document.getElementById('map');

   // 현재 나의 위치 이미지
   var imageSrc = "/img/mymarker.png",
      imageSizes = [new kakao.maps.Size(90, 90), new kakao.maps.Size(70, 70), new kakao.maps.Size(40, 40)], // 마커 이미지 크기 배열
      imageSizeIndex = 0, // 초기 마커 이미지 크기 인덱스
      imageOption = { offset: new kakao.maps.Point(16, 32) }; // 마커이미지의 옵션

   //imageSize = new kakao.maps.Size(32, 32), // 초기 마커 이미지의 크기
   //imageOption = {offset: new kakao.maps.Point(16, 32)}; // 마커이미지의 옵션(마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정)

   // 마커의 이미지정보를 가지고 있는 마커이미지를 생성
   var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSizes[imageSizeIndex], imageOption),
      markerPosition = new kakao.maps.LatLng(37.54699, 127.09598); // 마커가 표시될 위치

   mapOption = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 3
   };

   // 지도 객체 생성하기
   var map = new kakao.maps.Map(mapContainer, mapOption);

   // 클러스터러를 생성
   var clusterer = new kakao.maps.MarkerClusterer({
      map: map, // 마커들을 표시할 지도 객체
      averageCenter: true, // 중심 좌표를 클러스터의 평균 위치로 설정
      minLevel: 7, // 클러스터링이 되기 시작할 지도의 레벨
      disableClickZoom: true // 클러스터 마커를 클릭했을 때 지도가 확대되지 않도록 설정
   });

   // 현재 위치 버튼 클릭 시 발생
   const mylocation = document.getElementById("mylocation");

   mylocation.addEventListener("click", function() {
      // 현재 위치를 얻어오기
      navigator.geolocation.getCurrentPosition(function(position) {
         var latitude = position.coords.latitude; // 위도
         var longitude = position.coords.longitude; // 경도

         // 지도의 중심을 현재 위치로 설정
         var mapCenter = new kakao.maps.LatLng(latitude, longitude);
         map.setCenter(mapCenter);

         displayMarker(mapCenter);

      }, function(error) {

         // 위치 정보를 가져오지 못한 경우에 대한 처리
         console.error("현재 위치를 가져오지 못했습니다.");
         console.error(error);
      });
   })

   // 현재 나의 위치 지도에 마커를 표시하는 함수
   function displayMarker(mapCenter) {

      // 마커를 생성
      var marker = new kakao.maps.Marker({
         map: map,
         position: mapCenter,
         image: markerImage // 마커이미지 설정 
      });

      // 현재 나의 위치 지도의 확대 수준에 따라 마커 이미지 크기 조절
      kakao.maps.event.addListener(map, "zoom_changed", function() {
         var zoomLevel = map.getLevel();

         if (zoomLevel < 5) {
            // 확대 수준이 5미만일 때 작은 크기로 변경
            imageSizeIndex = 0;
         } else if (zoomLevel < 10) {
            // 확대 수준이 5이상 10미만일 때 중간 크기로 변경
            imageSizeIndex = 1;
         } else {
            // 확대 수준이 10이상일 때 큰 크기로 변경
            imageSizeIndex = 2;
         }

         // 현재 나의 위치 마커 이미지 업데이트
         markerImage = new kakao.maps.MarkerImage(imageSrc, imageSizes[imageSizeIndex], imageOption);
         marker.setImage(markerImage);

      })

   }


   // 검색 페이지 URL
   const url = `/api/places/search?type=${selectedType}&sido=${selectedSido}&gugun=${selectedGugun}`;

   xhr.onreadystatechange = function() {
      if (xhr.readyState === 4) {
         // 서버 응답이 완료된 경우
         if (xhr.status === 200) {
            const responseData = JSON.parse(xhr.responseText);

            // 마커를 담을 배열 생성
            const markers = [];

            // 검색 결과를 나타낼 div 요소 선택
            const searchResultDiv = document.getElementById("searchResults");

            searchResultDiv.innerHTML = '';

            // 클릭한 div 박스에서 열린 infowindow를 저장하는 변수
            let openInfowindow = null;

            // 받아온 JSON 데이터를 표시
            responseData.forEach(function(place, index) {
				
               const placeDiv = document.createElement("div");

               // 검색된 목록을 클릭했을 때, 지도에서 해당 이름 띄우기
               placeDiv.addEventListener("click", function() {

                  // 이전에 열린 infowindow가 있다면 닫기
                  if (openInfowindow) {
                     openInfowindow.close();
                  }

                  // 클릭한 마커에 해당하는 infowindow 열기
                  const infowindow = new kakao.maps.InfoWindow({
                     content: '<div style="width:max-content; padding: 5px;  text-align:center">' + place.name + '</div>',
                     removable: true,
                  });
                  
                  // infowindow창 열기
                  infowindow.open(map, new kakao.maps.Marker({ position: markerPosition }));
                  
                  // 열린 infowindow를 openInfowindow 변수에 저장
                  openInfowindow = infowindow;
                  
                  // 특정 확대 레벨로 지도 확대
                  const zoomLevel = 3;
                  map.setLevel(zoomLevel, {anchor: markerPosition});
                                
                  map.panTo(markerPosition);
                  
	                  // 맵의 다른 부분을 클릭했을 때, 열린 infowindow 없애기
	               map.addListener('click', function(){
					   if(openInfowindow){
						   openInfowindow.close();
						   openInfowindow = null;
					   }
				   });

               });
               
			   
               const nameElement = document.createElement("p");
			   nameElement.innerHTML = `<i class="fa-solid fa-location-dot"></i> ${place.name}`;
               placeDiv.appendChild(nameElement);
               
               nameElement.addEventListener('click', function(){
				   const placeInfo = encodeURIComponent(place.name);
				   window.location.href= `review?name=${placeInfo}`;
			   })

               const addressElement = document.createElement("p");
               addressElement.innerText = place.addr;
               placeDiv.appendChild(addressElement);

               const telElement = document.createElement("p");
			   telElement.innerHTML = `<i class="fa-solid fa-phone"></i> ${place.tel}`;
               placeDiv.appendChild(telElement);

               const hoursElement = document.createElement("p");
			   hoursElement.innerHTML = `<i class="fa-solid fa-clock"></i> ${place.time}`;
               placeDiv.appendChild(hoursElement);
               
              // 위 태그들에 CSS 클래스 추가
              placeDiv.classList.add("map-info-div");
              nameElement.classList.add("map-info-name");
              addressElement.classList.add("map-info-address");
              telElement.classList.add("map-info-tel");
              hoursElement.classList.add("map-info-hours");


               // 아래쪽 경계 추가
               if (index < responseData.length ) {
                  placeDiv.style.borderBottom = "1px solid #ccc";
               }

               // div요소를 검색 결과에 추가
               searchResultDiv.appendChild(placeDiv);


               // lat과 lon, name값을 배열에 추가
               lat.push(place.lat);
               lon.push(place.lon);

               var iwContent = '<div style="padding: 5px; text-align:center">' + place.name + '</div>';

               // 마커를 생성하고 배열에 추가
               const markerPosition = new kakao.maps.LatLng(place.lat, place.lon);
               const marker = new kakao.maps.Marker({
                  map: map,
                  position: markerPosition
               });

               markers.push(marker);

               var infowindow = new kakao.maps.InfoWindow({ content: iwContent })

               // 마우스 올리면 마커 위에 병원 이름 뜨게 하기
               kakao.maps.event.addListener(marker, 'mouseover', (function(marker, infowindow) {
                  return function() {
                     infowindow.open(map, marker);
                  };
               })(marker, infowindow));

               // 마우스 커서를 치웠을 때 병원 이름 사라지게 하기 
               kakao.maps.event.addListener(marker, 'mouseout', (function(marker, infowindow) {
                  return function() {
                     infowindow.close();
                  };
               })(marker, infowindow));

               kakao.maps.event.addListener(marker, 'click', function() {
                  // 클릭한 마커의 위치
                  var markerPosition = marker.getPosition();

                  // 현재 지도 레벨에서 2레벨 확대한 레벨
                  //var level = map.getLevel() + 2;

                  // 클릭한 마커의 위치로 부드럽게 이동하기
                  map.panTo(markerPosition);
               });
            });
            // 검색된 결과 중 첫번째 병원의 위치로 화면 이동 
            var moveLatLon = new kakao.maps.LatLng(lat[0], lon[0]);
            map.setCenter(moveLatLon);


            // 클러스터러에 마커들을 추가
            clusterer.addMarkers(markers);
         } else {
            // 서버에서 오류 응답을 받은 경우에 대한 처리
            console.error("서버에서 오류 응답을 받았습니다.");
         }
      }
   };

   // 마커 클러스터러에 클릭이벤트를 등록
   kakao.maps.event.addListener(clusterer, 'clusterclick', function(cluster) {

      // 현재 지도 레벨에서 1레벨 확대한 레벨
      var level = map.getLevel() - 1;

      // 지도를 클릭된 클러스터의 마커의 위치를 기준으로 확대
      map.setLevel(level, { anchor: cluster.getCenter() });
   });


   // Get 요청을 서버로 보냄
   xhr.open("GET", url, true);
   xhr.send();

});
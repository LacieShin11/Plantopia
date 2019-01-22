플랜토피아
==========

플랜토피아는 반려식물 교감 서비스로, 센서를 활용해 반려식물의 상태를 실시간으로 파악하고, 챗봇으로 식물과 대화를 나눌 수 있습니다.

프로젝트 개요
-------------

-	**개발 기간**
	-	2018년 6월 ~ 2018년 8월
-	**개발 인원**
	-	6명 (인원 구성 : 디자인 1명, 아두이노 1명, 서버+안드로이드 1명, 안드로이드 1명, 챗봇 2명)
-	**기술 스택**
	-	Sensor : C, Arduino
	-	Client App : Java, Android
	-	Back-end : Javascript, Node.js, MySQL
	-	Hosting : AWS
	-	Chatbot : IBM Watson API

주요 기능
---------

-	**회원가입 & 로그인**
	-	회원가입 시 이메일과 비밀번호를 입력받고, 이메일 중복 확인, 비밀번호 재확인 처리를 한다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/login.png?raw=true" width="250" />

-	**홈**
	-	다음 open API를 활용해 반려식물과 관련된 읽을거리와 제품 정보를 제공한다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/home.png?raw=true" width="250" />

-	**검색**
	-	농업기술포털의 실내정원용 식물 공공 데이터로 반려식물의 생육 정보를 제공한다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/search.png?raw=true" width="250" />

-	**식물 추가**
	-	아두이노 센서가 연결된 식물의 상태 값을 얻어오려면 지정된 ID를 입력해야한다.
	-	공공데이터에서 선택한 식물의 생육 온도와 습도값을 가져와 실시간 상태 측정 값의 상태 판단 기준으로 DB에 저장한다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/add_plant.png?raw=true" width="250" />

-	**사용자 정보**
	-	사용자가 추가한 식물, 다이어리, 스크랩 내역을 표시한다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/profile1.png?raw=true" width="250" />

-	**식물 상세정보**
	-	밝기, 습도, 온도 습도의 측정 값을 표시한다. 배터리 문제로 30분에 한 번씩 값이 업데이트된다.
	-	와이파이 모듈을 사용하기 때문에 화분이 와이파이 환경 속에 있을 때 동작한다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/status.png?raw=true" width="250" />

-	**챗봇**
	-	식물의 종에 따라 달라지는 말투로 챗봇과 대화를 나눌 수 있다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/chat.png?raw=true" width="250" />

-	**음악**
	-	챗봇 개발자가 영혼을 갈아 작곡한 힐링 음악을 들으며 재충전의 시간을 가질 수 있다.

<img src="https://github.com/zion830/Plantopia/blob/master/asset/music.png?raw=true" width="250" />

서비스 동작 구조
----------------

<img src="https://github.com/zion830/Plantopia/blob/master/asset/logic.png?raw=true" width="750" />

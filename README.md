# :sunrise_over_mountains: DAMDA

'여행을 담다' 라는 주제로 멋진 여행지들을 나만의 플래너에 담고 공유할 수 있는 서비스입니다.
주요 기능으로 **카카오 로그인, 여행지 탐색, 리뷰 작성, 여행 계획 세우기, 채팅, 플래너 공유** 기능이 있습니다.

## 프로젝트 기간
- 2022.08.01 ~ 2022.09.30 : 프로젝트 1차 개발
- 2022.10.01 ~ : 고도화 및 유지보수

## 팀원 & 역할
|김민재     |박재현     |장동익     |정혜수     |좌창화     |
|-----------|----------|----------|-----------|----------|
|여행 플래너 개발|여행지 탐색 개발|회원 기능, 플래너 공유 개발|리뷰 기능 개발|채팅 기능 개발|

## 📚 Stacks
<div align=center> 
  <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white"> 
  <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"> 
  <img src="https://img.shields.io/badge/kakao API-FFCD00?style=for-the-badge&logo=kakao&logoColor=black"> 
  <img src="https://img.shields.io/badge/java17-007396?style=for-the-badge&logo=java&logoColor=white"> 
  <img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white"> 
  <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white"> 
    <img src="https://img.shields.io/badge/nginx-009639?style=for-the-badge&logo=nginx&logoColor=white"> 
</div>

## Git Convention

### Branch

|이름             |설명                          |
|----------------|-------------------------------|
|`master`    |`배포용 마스터 브랜치`           |
|`devbranch`    |`배포 전 개발 브랜치`           |
|`feature`   |`기능 개발 ex. User의 login 관련 기능을 개발하는 경우: feature-user-login`             |
|`hotfix`    |`버그 수정 ex. User의 login 관련 버그를 수정하는 경우: hotfix-user-login`               |
|`other`     |`리팩토링, 문서작성, 설정변경 등`  |

## ERD
https://www.erdcloud.com/d/bf5syqucdbTAtntsq
![image](https://user-images.githubusercontent.com/86871368/201333693-322699c7-7bee-4198-a309-fe67af77c207.png)
## 핵심 기능

### 1. 로그인 및 회원 가입
-   로그인 & 회원가입
    -   Spring Security를 이용한 로그인/로그아웃
    -   쿠키와 세션을 이용한 자동로그인 기능
    -   아이디 찾기와 Google SMTP로 비밀번호 찾기 가능
-   소셜 로그인
    -   Oauth2를 이용한 Kakao 소셜 로그인

### 2. 여행지 탐색
-   태그 기반 여행지 검색 페이지
    -   여행지 이름과 태그를 기준으로 여행지 여행지 검색
    -   여행지 주요 태그 조회 가능
    -   태그 기반 정렬, 리뷰 갯수 기반 정렬
-   여행지 상세 페이지
    -   카카오 이미지 검색 API로 받아온 이미지 볼 수 있음
    -   해당 여행지의 리뷰 작성이 가능
    -   해당 여행지를 장바구니에 담을 수 있음

### 3. 리뷰 작성
-   리뷰 작성 및 조회
    -   지정 태그를 포함한 여행지에 대한 리뷰 작성
    -   여행지 별 리뷰 조회 페이지
    -   접속자 계정의 리뷰 조회 페이지
    
### 4. 여행 계획 작성
-   여행 코스 설계 페이지
    -   링크를 통해 다른 사용자에게 여행 계획 공유 가능
    -   사용자들이 수정한 내용은 실시간으로 모든 사용자에게 반영
    -   웹 소켓을 통한 실시간 채팅
    -   여행지 탐색에서 담은 여행지를 장바구니에서 조회 가능
    -   카카오 맵 API에서 검색한 여행지를 장바구니에 담기 가능
    -   카카오 맵 API에서 검색한 여행지를 여행 코스에 직접 추가 가능

### 5. 채팅
- 웹소켓 기반의 채팅 기능

### 6. 플래너 공유

## Architecture
![Architecture](https://user-images.githubusercontent.com/86871368/193774180-073e2ace-833f-4f7e-be52-b8563b15e19b.png)

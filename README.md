# CS496 Imersive Camp (Winter 2021)

# Project #1: Android Application

## Team

강산아 ([https://github.com/sanakang0615](https://github.com/sanakang0615))
송성민 ([https://github.com/reverg](https://github.com/reverg))

## Introduction

이 앱의 이름은  `PocketApp`이다. 연락처, 갤러리, 보이스 노트 기능을 각각의 탭에 넣어 3개의 탭으로 구성되었다. 앱이 실행될 때 시작 화면을 띄웠다가 MainActivity가 실행된다.

## Tab 1: Contact

### Features

- 기기의 연락처 데이터 받아오기
- 프로필 사진 
- 통화 / 메세지 전송으로 이동
- 연락처 검색
- 기기에 연락처 추가

### Function 1: Get Contact Data from Device

 기기의 연락처 데이터를 받아와서 화면에 나타낸다. Android가 Marshmallow version 이상인 경우 연락처에 접근하기 위한 권한을 얻어야 하는데, 이를 위해 앱 설치 후 최초 실행시에 연락처 접근을 요청하는 창이 뜨도록 하였다. 연락처를 띄우는데는 RecyclerView를 사용하였으며 사전 순서로 정렬시켰다. 

### Function 2: Show Profile Image

 표시된 연락처를 누르면 연락처에 설정된 프로필 이미지를 화질 저하 없이 크게 볼 수 있게 만들었다. 별도의 창으로 나타나며 이름과 전화번호도 같이 표시된다.
 
 <img src = "https://user-images.githubusercontent.com/48681924/148014507-fcdb471e-816a-4a96-ac26-4512d78ee7d1.jpg" width="20%" height="20%">
 
### Function 3: Move to Call / Message

 연락처 우측의 전화 / 문자 버튼을 누르면 해당하는 기능으로 이동하게 만들었다. Android의 기본 전화, 문자 기능으로 Intent를 통해 이동하게 하였고, 전화번호 값을 가지고 가도록 하여 사용자가 별도 입력 없이 바로 원하는 작업을 할 수 있게 하였다.

### Function 4: Search Contact

 우측 하단의 Floating Action Buttion(FAB)을 누르면 2개의 버튼이 추가로 나타난다. 연락처 검색 기능은 Search가 옆에 적혀있는 돋보기 모양의 위쪽 버튼을 누르면 작동한다. 작동시 EditText와 X button의 Visibility가 Visible로 바뀌면서 타이핑 할 수 있는 부분이 나타나고 FAB을 눌렀을 때 나타났던 2개의 버튼은 다시 사라지게 된다.

  <img src = "https://user-images.githubusercontent.com/48681924/148014438-54d22732-76ed-4c30-8d87-0a471353b389.jpg" width="20%" height="20%">

 글자가 입력될 때마다 Device에서 받아온 Contact들과 이름 값을 비교해 실시간으로 검색어가 이름에 포함되어 있는 연락처들을 표시한다. 한글은 음절별로 검색이 진행되며, 영어는 글자별로 검색된다(대, 소문자 무시). 검색어가 입력되지 않은 경우에는 전체 리스트를 띄운다.
 
  <img src = "https://user-images.githubusercontent.com/48681924/148014178-6be17884-537d-43ea-bf8f-c9cc51379d06.jpg" width="20%" height="20%">

 검색을 중지할 때는 X button을 누르면 된다. EditText와 X button이 모두 사라지며 입력되었던 검색어도 전부 지워지게 된다.

### Function5: Add Contact in Device

 Function 4때 설명한 FAB을 누르면 나오는 2개의 버튼 중 아래 버튼에 할당된 기능이다. 누르면 Android의 기본 연락처 기능 중 연락처 추가 화면으로 이동하게 된다. 연락처를 추가하고 `PocketApp`으로 돌아온 후에 연락처를 수동으로 새로고침해줘야 한다. 새로고침은 화면을 위에서 아래로 Swipe하면 수행되며, 이를 위해 Layout에 SwipeRefreshLayout이 적용되었다.

## Tab 2: Gallery

### Features

- 앱 내의 사진 띄우기
- 기기의 갤러리 사진 받아와서 띄우기
- 사진 검색하기

### Function 1: Show Images of Application

 

### Function 2: Get Image from Device

 연락처 우측의 전화 / 문자 버튼을 누르면 해당하는 기능으로 이동하게 만들었다. Android의 기본 전화, 문자 기능으로 Intent를 통해 이동하게 하였고, 전화번호 값을 가지고 가도록 하여 사용자가 별도 입력 없이 바로 원하는 작업을 할 수 있게 하였다.

### Function 3: Search Images

 우측 하단의 Floating Action Buttion(FAB)을 누르면 2개의 버튼이 추가로 나타난다. 연락처 검색 기능은 Search가 옆에 적혀있는 돋보기 모양의 위쪽 버튼을 누르면 작동한다. 작동시 EditText와 X button의 Visibility가 Visible로 바뀌면서 타이핑 할 수 있는 부분이 나타나고 FAB을 눌렀을 때 나타났던 2개의 버튼은 다시 사라지게 된다.

 글자가 입력될 때마다 Device에서 받아온 Contact들과 이름 값을 비교해 실시간으로 검색어가 이름에 포함되어 있는 연락처들을 표시한다. 한글은 음절별로 검색이 진행되며, 영어는 글자별로 검색된다(대, 소문자 무시). 검색어가 입력되지 않은 경우에는 전체 리스트를 띄운다.

 검색을 중지할 때는 X button을 누르면 된다. EditText와 X button이 모두 사라지며 입력되었던 검색어도 전부 지워지게 된다.

## Tab 3: Voice Note

### Features

- 음성 인식
- 인식된 텍스트 복사

### Function 1: Speech-to-Text

 우측 하단의 FAB을 누르면 Google Speech API에서 제공하는 음성 인식 기능으로 이동된다. 말이 시작되고 멈출 때까지 한국어를 인식하여 말이 끝나면 화면에 인식된 텍스트를 표시한다.

### Function 2: Copy Text

 편의성을 위해 음성 인식된 텍스트가 표시된 화면을 탭하면 자동으로 클립보드에 저장되도록 하였다. 텍스트를 길게 눌러서 복사하거나 공유하는 것도 가능하다.

---

[https://github.com/reverg/MadCamp1](https://github.com/reverg/MadCamp1)

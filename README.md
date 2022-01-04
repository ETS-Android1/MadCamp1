# CS496 Immersive Camp (Winter 2021)

# Project #1: Android Application

## Team
강산아 ([https://github.com/sanakang0615](https://github.com/sanakang0615))

송성민 ([https://github.com/reverg](https://github.com/reverg))

## Introduction

이 앱의 이름은 `PocketApp`이다. 연락처, 갤러리, 보이스 노트 기능을 각각의 탭에 넣어 3개의 탭(Fragment)으로 구성하였다. 앱이 실행될 때 일정 시간 동안 시작 화면을 띄운다 `MainActivity`를 실행한다.

## Tab 1: Contact

### Features

- 기기의 연락처 데이터 받아오기
- 프로필 사진 
- 통화 / 메세지 전송으로 이동
- 연락처 검색
- 기기에 연락처 추가

### Function 1: Get Contact Data from Device

 기기의 연락처 데이터를 받아와서 화면에 나타낸다. Android가 `Marshmallow version` 이상인 경우 연락처에 접근하기 위한 권한을 얻어야 하는데, 이를 위해 앱 설치 후 최초 실행시에 연락처 접근을 요청하는 창이 뜨도록 하였다. 연락처를 띄우는데는 `RecyclerView`를 사용하였으며 사전 순서로 정렬시켰다. 

### Function 2: Show Profile Image

 표시된 연락처를 누르면 연락처에 설정된 프로필 이미지를 화질 저하 없이 크게 볼 수 있게 만들었다. 별도의 창으로 나타나며 이름과 전화번호부도 같이 표시된다.
 
 <img src = "https://user-images.githubusercontent.com/48681924/148014507-fcdb471e-816a-4a96-ac26-4512d78ee7d1.jpg" width="20%" height="20%">
 
### Function 3: Move to Call / Message

 연락처 우측의 전화 / 문자 버튼을 누르면 해당하는 기능으로 이동하게 만들었다. Android의 기본 전화, 문자 기능으로 `Intent`를 통해 이동하게 하였고, 전화번호 값을 가지고 가도록 하여 사용자가 별도 입력 없이 바로 원하는 작업을 할 수 있게 하였다.

### Function 4: Search Contact

 우측 하단의 `Floating Action Buttion(FAB)`을 누르면 2개의 버튼이 추가로 나타난다. 연락처 검색 기능은 Search가 옆에 적혀있는 돋보기 모양의 위쪽 버튼을 누르면 작동한다. 작동시 `EditText`와 `X button`의 Visibility가 Visible로 바뀌면서 타이핑 할 수 있는 부분이 나타나고 FAB을 눌렀을 때 나타났던 2개의 버튼은 다시 사라지게 된다.
 
 <img src = "https://user-images.githubusercontent.com/48681924/148014438-54d22732-76ed-4c30-8d87-0a471353b389.jpg" width="20%" height="20%">

 글자가 입력될 때마다 Device에서 받아온 Contact들과 이름 값을 비교해 실시간으로 검색어가 이름에 포함되어 있는 연락처들을 표시한다. 한글은 음절별로 검색이 진행되며, 영어는 글자별로 검색된다(대, 소문자 무시). 검색어가 입력되지 않은 경우에는 전체 리스트를 띄운다.
 
 <img src = "https://user-images.githubusercontent.com/48681924/148014178-6be17884-537d-43ea-bf8f-c9cc51379d06.jpg" width="20%" height="20%">

 검색을 중지할 때는 `X button`을 누르면 된다. `EditText`와 `X button`이 모두 사라지며 입력되었던 검색어도 전부 지워지게 된다.

### Function5: Add Contact in Device

 Function 4때 설명한 FAB을 누르면 나오는 2개의 버튼 중 아래 버튼에 할당된 기능이다. 누르면 Android의 기본 연락처 기능 중 연락처 추가 화면으로 이동하게 된다. 연락처를 추가하고 `PocketApp`으로 돌아온 후에 연락처를 수동으로 새로고침해줘야 한다. 새로고침은 화면을 위에서 아래로 Swipe하면 수행되며, 이를 위해 Layout에 `SwipeRefreshLayout`이 적용되었다.

## Tab 2: Gallery

### Features

- Default: 앱 내에 저장되어 있는 사진 띄우기
- 휴대폰 갤러리 모드: 휴대폰 기기의 갤러리 사진을 선택하여 띄우기
- Pixabay 이미지 검색: `Pixabay API`를 이용하여 웹 이미지 검색하기
- 이미지 확대/축소 기능: 더블클릭 또는 두 손가락 스크롤으로 이미지 확대 및 축소하기

### Function 1: Show Images of Application

아래에 있는 이미지 목록 중 하나를 선택하면 상단에 사진이 크게 뜨게 만들었다. 이미지 목록은 `Gallery Viewer`를 이용하여 Horizontal하게 구현하였으며 animation duration 시간을 조절하여 자연스럽게 넘어가게 하였다. `com.github.chrisbanes:PhotoView`를 이용하여 상단에 뜬 사진을 더블 클릭하거나 두 손가락 스크롤을 통해 확대 및 축소할 수 있게 하였다. `Custom Viewpager`을 선언하여 이용하였다.


### Function 2: Get Image from Device

`휴대폰 갤러리 모드` 버튼을 클릭하면 휴대폰 기기에 내장된 갤러리로 이동하게 만들었다. 그런 뒤, 갤러리에서 사진을 하나 클릭하면 선택한 이미지에서 비트맵을 생성하여 이미지뷰에 해당 사진이 세팅되게 만들었다.


### Function 3: Search Images
`Pixabay 이미지 검색` 버튼을 클릭하면 해당 프래그먼트에서 새로운 액티비티로 이동한다. 액티비티 상단에 있는 검색 기능을 통해 검색어를 입력하면, `Pixabay API`의 고유키를 이용하여 url을 생성해서 request를 보낸다. 이후, 해당 request에 대해 JSON Array를 받아오고 각 오브젝트에 대한 image url을 받아와서 이미지 리스트를 만든다. 그리고 해당 이미지 리스트를 `Recycler view`를 통해 액티비티에 뜨게 만들었다. 이미지 검색이 끝난 후, `이전으로 돌아가기` 버튼을 클릭하면 기존의 갤러리 탭으로 다시 돌아가게 만들었다.

## Tab 3: Voice Note

### Features

- 음성 인식 후 인식한 텍스트 띄우기 (지원 언어: 한국어 / 영어)
- 인식된 텍스트 복사

### Function 1: Speech-to-Text

우측 하단의 `Floating Action Buttion(FAB)`을 누르면 언어(한국어/영어)를 선택할 수 있는 버튼이 나타난다. 사용자가 특정 언어를 선택하면 우측 하단의 스피커 모양 FAB을 누르면 음성 인식 기능을 비동기 실행하여 `Google Speech API`에서 제공하는 음성 인식 기능으로 이동된다. 그 뒤, 사용자가 설정한 언어를 기반으로 인식하여 화면에 텍스트가 표시된다.

### Function 2: Copy Text

 사용자 편의를 위해 `Clipboard Manager`를 이용하여 필기 완료된 텍스트를 한번 누르면 자동으로 클립보드에 저장되도록 하였다..


---


[https://github.com/reverg/MadCamp1](https://github.com/reverg/MadCamp1)

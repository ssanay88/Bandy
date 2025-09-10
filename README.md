# 🎸 Bandy
<p align="center">
  <img width="128" height="128" src="https://github.com/user-attachments/assets/3a63f4a8-6c5c-42e5-a388-d0fe9ac1c95d" alt="ic_bandy" />
</p>

<p align="center">
  <strong>밴드붐은 이미 왔다!</strong><br />
  청소년부터 직장인까지, 합주 초보부터 전문가까지 모두 의견을 공유하고<br />
  물어보고 자신의 밴드를 관리할 수 있는 앱입니다.
</p>

---
## 🛠️ 프로젝트
- **앱 이름** : Bandy
- **참여 인원** : 1인 기획 / 개발
- **개발 기간** : 2025.08.05 ~ 마켓 등록 준비 중
- **플랫폼** : Android  
- **개발 언어** : Kotlin  
- **개발 환경** : Android Studio  
- **데이터** : Firestore를 이용한 Serverless  

## 📃 프로젝트 기획 의도
취미로 밴드를 시작하면서 합주를 준비할 때 필요한 정보가 많고 흩어져 있다는 것을 느꼈고, 이를 겪는 사람들이 생각보다 많다는 걸 알게 되었습니다. 이런 경험을 바탕으로, 밴드 정보를 쉽게 공유하고 자랑할 수 있는 플랫폼을 개발하고자 했습니다.

---  

## 📌 기술 스택
| 항목              | 사용 기술                            |
|-------------------|--------------------------------------|
| Programming Language | Kotlin                              |
| Architecture       | MVVM, Clean Architecture            |
| UI                | Compose Material3 UI/UX             |
| Asynchronous      | Coroutine, StateFlow / SharedFlow   |
| Navigation        | Compose Navigation                  |
| Local Storage     | DataStore                           |
| Networking        | Firebase Firestore                  |
| Image Loading     | Coil                                |

---  

## 📱 주요 기능 동작 및 설명
### 1. 앱 시작
| 스플래시 화면  |**네이버 아이디로 로그인**|
|-------------|-------------------|
|![스플래시 영상](https://github.com/user-attachments/assets/7cc6af6c-9cad-43ff-8e7f-02cdb97cd482)|![네이버 로그인 영상](https://github.com/user-attachments/assets/8b240986-fa4d-480e-8837-06b2e45d13cd)|
  
---

### 2. 프로필 등록
최초 로그인인 경우 프로필 등록 화면으로 이동합니다.  

|**프로필 이미지 등록**| **프로필 설정** |
|------------------|--------------|
|![프로필 이미지 등록](https://github.com/user-attachments/assets/c06612ac-12a1-48e3-ba4b-28d33cbc3b19)|![프로필 등록](https://github.com/user-attachments/assets/ec90002e-33da-462a-9c6e-5b7f5b67582c)|

---

### 3. 홈 화면
|**전체 구성**| **게시물 클릭** |
|-----------|---------------|
|![홈 화면 영상](https://github.com/user-attachments/assets/b5f1cb94-3354-4291-a3f7-bfca85f40453)|![게시물 및 댓글 영상](https://github.com/user-attachments/assets/7b82573f-a0c4-429a-b5c7-06fd21cce083)|
|- 상단에는 광고를 위한 배너가 위치합니다.<br> - 중단에는 현재 인기 밴드를 볼 수 있습니다. <br> - 하단에는 인기 게시물과 전체 게시물을 볼 수 있습니다.| - 게시물을 볼 수 있고 댓글을 입력할 수 있습니다. |   

|**게시물 작성**| **게시물 등록 완료** |
|-----------|---------------|
|![게시물 작성 및 등록](https://github.com/user-attachments/assets/439487c2-6d6f-4c67-8f34-befcd5ef3aff)|![게시물 등록 - 상세](https://github.com/user-attachments/assets/ce50951c-f19a-41ed-9da2-8e163a10f3cc)|
|- 게시물 유형을 선택 후 내용을 작성하여 등록합니다.|- 작성한 게시물이 등록되었습니다.|  

---

### 4. 모집 화면
|**밴드 찾기**| **멤버 찾기** |
|-----------|----------------|
| ![밴드 모집 화면 영상](https://github.com/user-attachments/assets/02ba64dd-4ca9-4235-8597-318c453538a0)|![멤버 모집 화면 영상](https://github.com/user-attachments/assets/a0acba42-9ad0-4318-af0c-ae0af624168e)|
|- 멤버를 모집 중인 밴드를 확인할 수 있습니다.|- 필요한 조건에 맞는 멤버를 모집중인 공고를 확인할 수 있습니다.|  


|**밴드 생성**| **멤버 공고글 작성** |
|-----------|----------------|
|![밴드 생성 - 멤버 추가 영상](https://github.com/user-attachments/assets/905f85e9-0547-4c10-8d2a-be1da7bd73f1)|![멤버 보집 공고 생성 영상](https://github.com/user-attachments/assets/5e620ffb-657f-457d-b7df-f206b9ab41ed)|
|- 자신과 밴드원을 초대하여 밴드를 생성할 수 있습니다. |- 원하는 조건을 설정하여 앎맞은 멤버 모집 공고글을 작성할 수 있습니다. |

---  

### 5. 마이 밴드
|**소속 밴드가 없는 경우**| **소속 밴드가 있는 경우** |
|--------------------|-----------------------|
|![마이 밴드 - 소속 밴드 없는 경우](https://github.com/user-attachments/assets/8d038493-ba5d-4907-9a62-8672cd5c3d81)|![마이 밴드 - 소속 밴드 있는 경우](https://github.com/user-attachments/assets/a0b3dbff-fa23-46c6-a1f5-d7216638d6e7)|
|- 소속된 밴드가 없는 경우 '모집 화면'으로 이동을 유도합니다.|- 소속된 밴드가 있는 경우 밴드의 정보를 볼 수 있습니다.|

--- 

### 6. 채팅
|**채팅**|
|-------|
|![채팅 방 영상](https://github.com/user-attachments/assets/c94cb8ab-7ea0-4ab9-9d5a-17175e7b72d1)|
|- 실시간 채팅으로 밴드 멤버들 혹은 다른 유저들과 채팅이 가능합니다.|

--- 
### 7. 프로필
|**프로필 변경**|
|-------|
|![프로필 변경 영상](https://github.com/user-attachments/assets/b2cda634-6db7-40ce-879a-5927e90f86e5)|
|- 자신의 프로필 정보를 확인하고 변경할 수 있습니다.|





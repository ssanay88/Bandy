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
- **개발 기간** : 2025.06.16 ~ 마켓 등록 준비 중
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
| 스플래시 화면  |  네이버 아이디로 로그인 |
|-------------|-------------------|
|![Bandy-스플래시 화면 수정본](https://github.com/user-attachments/assets/37c34b02-4a13-425f-b7e7-d9afe73fe53c) | ![Bandy-네이버 로그인](https://github.com/user-attachments/assets/c52e1db4-ff55-415e-9f84-0997e5f01970)|
  
---

### 2. 프로필 등록
최초 로그인인 경우 프로필 등록 화면으로 이동합니다.  

|**프로필 이미지 등록**| **프로필 설정** |
|------------------|--------------|
|![Bandy-프로필 이미지 등록](https://github.com/user-attachments/assets/abd371e6-383b-438f-ab14-571aa0ae51b1)|![Bandy-프로필 등록](https://github.com/user-attachments/assets/a06c0485-13ff-4e90-bb76-a3f232e99d2e)|

---

### 3. 홈 화면
|**전체 구성**| **인기 밴드 클릭** | **게시물 클릭** |
|-----------|----------------|---------------|
| ![Bandy-홈 화면(경량)](https://github.com/user-attachments/assets/1fae37c4-d3f6-44c6-97de-92785920dfbd)| ![Bandy-인기 밴드 선택](https://github.com/user-attachments/assets/c21d4354-b0a4-4694-84a7-a8bf16fd3084) | ![Bandy-게시물 및 댓글](https://github.com/user-attachments/assets/6d5cf610-cde6-4972-bd9d-e25c7988840a) |
|- 상단에는 광고를 위한 배너가 위치합니다.<br> - 중단에는 현재 인기 밴드를 볼 수 있습니다. <br> - 하단에는 인기 게시물과 전체 게시물을 볼 수 있습니다. | - 밴드 상세 정보를 볼 수 있는 페이지로 이동합니다. | - 게시물을 볼 수 있고 댓글을 입력할 수 있습니다. |

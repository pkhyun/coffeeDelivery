# ☕CoffeeDeliveryProject
- 프로젝트명 : 🛵우리동네 달리는 카페 
- 내용 : 우리동네 달리는 카페는 커피 배달 서비스로 "사용자"는 카페를 조회하여 해당 카페에서 판매하고 있는 음료 등을 주문 할 수 있고
  카페 주인은 "서버 관리자"를 통해 카페에 메뉴, 주문상태 등을 관리 할 수 있는 프로젝트 입니다.
  
  
# 🖥️Tech Stack
- 언어 : Java
- 버전 : JDK17
- Tools : GitHub, Git
- IDE : IntelliJ IDEA
- DB: MySQL 8.0.37
- Framework : SpringBoot 3.2.5
- 인증/인가 방식 : JWT

[![Top Langs](https://github-readme-stats.vercel.app/api/top-langs/?username=dlalwn&layout=compact)](https://github.com/dlalwn/github-readme-stats)
  
# 👉팀원 소개 & 역할 분담
<details>
<summary>
  ${\textsf{\color{green}🤡이창형(팀장)}}$
</summary>
  <br>
   - Admin 회원 CRUD<br>
   - 로그아웃 API 
</details>
<details>
<summary>
  ⚡김승수
</summary>
  <br>
   - 카페 페이지 단건, 전체 조회<br>
   - 카페 페이지 CRUD<br>
   - 메뉴 CD<br>
</details>
<details>
<summary>
  👍🏻김현민
</summary>
  <br>
   - 회원가입, 로그인API<br>
   - 엑세스 토큰 재발급 API
</details>
<details>
<summary>
  💦박강현
</summary>
  <br>
   - 주문 CRUD<br>
   - 좋아요 CD
</details>
<details>
<summary>
  ✔️최지연
</summary>
  <br>
   - 리뷰 CRUD
</details>

# 🎬프로젝트 소개
☕프로젝트 이름은 우리동네 달리는 카페로 커피 전문 배달 앱을 만들었습니다.🍹<br><br>
<details>
<summary>
  1️⃣ 회원 관리
</summary>
  <br>
   • 회원 가입 및 로그인/로그아웃<br>
    - 사용자는 간편하게 회원 가입하고, 로그인/로그아웃 기능을 통해 자신의 계정을 관리 가능<br><br>
   • 프로필 수정<br>
    - 일반 유저는 자신의 프로필을 수정 가능<br>
</details>
<details>
<summary>
  2️⃣ 관리자(admin) 유저 기능
</summary>
  <br>
   •회원 관리<br>
    - 전체 회원 조회<br>
    - 특정 회원 정보 수정 및 삭제<br>
    - 회원 차단<br>
    - 일반 유저를 관리자로 권한 변경<br><br>
  •카페 페이지 관리<br>
    - 카페 등록, 조회, 수정, 삭제<br><br>
  •카페 메뉴 관리<br>
    - 메뉴 등록, 삭제<br><br>
  •주문 관리<br>
    - 주문 상태 변경- 주문 취소<br>
</details>
<details>
<summary>
  3️⃣ 일반 유저 기능
</summary>
  <br>
   •카페 페이지 조회<br>
    - 다양한 카페 페이지를 조회 가능<br><br>
  •주문 작성 및 조회<br>
    - 커피를 주문하고 주문 내역을 조회 가능<br><br>
  •리뷰 기능<br>
    - 리뷰 작성, 수정, 조회, 삭제<br>
    - 카페 페이지와 리뷰에 좋아요 및 좋아요 취소<br>
    - 단, 리뷰 수정, 삭제, 좋아요 취소는 본인이 작성한 경우에만 가능<br>
</details>


# 📢Github Rules & Code Convention
<details>
<summary>
  ⚖️Github Rules
</summary>
  <br>
   • 브랜치 이름 규칙<br><br>
    - dev 브랜치, 각자 개발 기능 구현 feat/(기능이름) 브랜치<br>
    - 두가지 단어라면 ‘ - ’ (하이픈) 사용해서 구분<br>
    - feat/signup, feat/order-create<br><br>
• 커밋 메시지 규칙<br><br>
    - ~~[feat] #1(이슈 번호) 로그인 함수 구현~~<br>
    - **✨ update - #1 로그인 함수 구현 (이모지는 밑에 상황별 이모지)**<br>
    - ✨ update, 🩹 fix - #1 로그인 함수 구현, 회원가입 함수명 수정<br><br>
• 이슈 작성 규칙<br><br>
    - 이슈 사용<br>
    - title : 🌟 [feat] 이슈명 / description : 템플릿 따라서 작성<br>
    - title : 👾 [bug]  이슈명 / description : 템플릿 따라서 작성<br><br>
• PR 작성 규칙<br><br>
    - pr 규칙 사용 [현재날짜] 브랜치명 >> 간단한 설명<br>
    - [2024/06/19] feat/signup(브랜치명) >> 로그인 기능 구현(구현한 것 간단하게)<br><br>
• 코드리뷰 적용 (리뷰 1개이상 머지 가능, 겹치는 부분은 해당 담당자에게 리뷰 받기 필수)
</details>
<details>
<summary>
  🔑Code Convention
</summary>
  <br>
   • 카멜케이스 함수명, 변수명에 적용<br> ex) ‘userLogin’<br>
   • 폴더는 소문자<br>
   • 파일은 파스칼 케이스 적용<br>  ex) ‘UserController’<br>
   • 함수 만들때 동사 → 명사 순서<br> ex) ’updateUser’<br>
   • 규격 잘 맞추기<br>
   • 주석 잘 달아주기!! (한 줄 이내)<br>
   • 클래스 이름 바로 밑의 줄 한줄 띄우기<br>
   • return 있을 때는 한줄 띄우지 말고 바로 `}`  작성<br>
   • return 없을 때는 맨 밑줄 한줄 띄우고 `}`  작성<br>
</details>

# 📑API 명세서
![hyunmin_2024-06-25_11-04-54](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/aeda900e-2988-4cde-a231-1ce92826e61a)

![changhyung_2024-06-25_11-05-37](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/baf12e43-f5a3-4166-93a5-f5c0b44bced9)

![seungsu_2024-06-25_11-06-13](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/06cb299b-2941-4f35-8ce0-3c6bca7fc3f1)

![kanghyun_2024-06-25_11-06-50](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/f24f8e4a-0b61-4155-a50f-813a3ae6cb12)

![jiyeon_2024-06-25_11-07-23](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/ea1fd651-b868-4c96-b8ba-0f01c31799ea)


![kanghyun-2_2024-06-25_11-08-06](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/a442b908-cb1a-47e7-a0e2-cba15a133c4a)

![changhyung-2_2024-06-25_11-08-34](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/42025462-a776-4480-8c01-ff397f093114)

![seungsu-2_2024-06-25_11-08-57](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/4d0edb99-0dda-471e-b26e-448320d1e121)

![suengsu-3_2024-06-25_11-09-29](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/d0605b55-0e37-494d-a67c-fdbb93dee860)

![kanghyun-3_2024-06-25_11-10-01](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/de6fe7e1-b078-4504-a209-b9b95251bfff)

# 📈ERD 다이어그램
![Snipaste_2024-06-25_10-57-19](https://github.com/LeeChangHyeong/CoffeeDeliveryProject/assets/166034905/e1aba161-31a7-420c-baaa-fea9a50c630f)

# 🔊트러블 슈팅
### 🚨 과거 비밀번호 배열에서 비밀번호가 3개 이상으로 저장되는 버그
<details>
<summary>
   버그 발생 상황 😱
</summary>
  <br>
   - 패스워드 인코더 적용<br>
   - 회원 비밀번호 3번 수정<br>
   - 회원 비밀번호 4번째 수정시에도 과거 비밀번호가 DB에 저장<br><br>
  🧨시스템에 패스워트 인코더를 적용하여 회원 비밀번호를 저장 및 검증하는 방식을 구현했다. 비밀번호는 최대 4개까지 저장되어야 하지만, 이전에 만들어진 비밀번호가 삭제되지 않고 계속 저장되는 버그가 발생
</details>

<details>
<summary>
  예상되는 오류 이유 🤔
</summary>
   <br>
  - 비밀번호를 삭제하는 로직에서 remove가 제대로 작동하지 않음<br>
  - 배열의 크기가 제대로 측정되지 않음<br>
  - 비밀번호를 저장하는 자료구조가 set이여서 발생<br>
</details>
<details>
<summary>
  오류 발생 원인 ❓
</summary>
   <br>
  • 🔎비밀번호 인코딩 문제<br>
    - 같은 비밀번호라도 패스워드 인코더가 서로 다른 인코딩 값으로 변환
</details>
<details>
<summary>
  문제 해결 🪄
</summary>
   <br> 
  • 비밀번호 비교 시 contains 메서드를 ${\textsf{\color{red}matches}}$ 메서드로 변경<br>
  • 과거 비밀번호를 저장하는 자료구조를 set에서 ${\textsf{\color{red}ArrayList}}$로 변경하여 동일한 비밀번호가 정확하게 비교되고 저장되도록 수정
</details>

# 📓회고
• 좋았던 점 ❤️
  <details>
<summary>
  🗣️ 팀원들 간의 원활한 소통
</summary>
   <br>
    1. 프로젝트를 진행하다가  모르는 부분이 생기면 언제들지 질문할 수 있었고, 팀원들은 질문에 대해 친절하게 대답을 해주고 같이 고민을 해주었다. <br><br>
    2. 팀 프로젝트 초반에 팀 회의를 통해 Git 규칙과 코드 컨벤션을 정했다.<br>
        - Git 규칙을 정함으로써 각자 작업 내용을 체계적으로 관리할 수 있었다.<br>
        - 코드 컨벤션을 정해 팀원들이 작성한 코드가 일관된 스타일을 유지항 가독성이 높아졌다.<br><br>
    💫 팀 소통과 초기 규칙 설정 덕분에 프로젝트를 원활하게 진행할 수 있었고 각자의 역할을 효과적으로 수행할 수 있었던 것 같다. 그리고 팀원들 간의 협업이 잘 이루어져서 프로젝트 결과물도 만족스러운 프로젝트였던 것 같다.<br><br>
</details>

• 아쉬운 점 💔
<details>
<summary>
  🥲 기능 추가에 대한 아쉬움
</summary>
   <br>
   프로젝트 진행 중 예상치 못한 오류들이 중간중간 발생하여 이를 해결하는데 시간이 소요되기도 하는 등 여러 문제로 인해 기능을 더 구현하지 못했다.<br>
   이번 프로젝트에서 초반에 목표한 기능들을 모두 구현했지만, 기능 추가에 대한 아쉬움이 있다.<br>
</details>

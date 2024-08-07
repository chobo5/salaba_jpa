## 개요
**사라바 프로젝트 🧳**
<br/>
한달살기를 주제로 여행에 있어 해당 지역에 대한 행사, 소식 뿐만 아니라 
<br/>
지역 커뮤니티를 형성하여 하나의 서비스내 에서 여행, 커뮤니티를 
<br/>
함께 즐길 수 있는 서비스를 만들어보자는 취지에서 시작하게 된 서비스 입니다. 
<br/>

## 역할
- **관리자 페이지**
    - Jwt를 활용한 SpringSecurity 적용 - 인증, 인가
    - 회원 관리, 숙소심사, 신고 처리, 문의 답변
- **커뮤니티**
    - 댓글, 답글 - jQuery와 Ajax 요청을 통해 댓글과 답글을 추가, 수정, 삭제
    - 좋아요 - jQuery, Ajax, Debouncing 기법을 활용
- **숙소결제 API, 구글 로그인 API**

## 기술스택&개발도구
![사용 기술](https://github.com/chobo5/salaba/assets/102145785/0e0bb9a2-4449-447e-ab35-1b09f24a4087)

## 주요기능
![주요 기능 사이트맵](https://github.com/chobo5/salaba/assets/102145785/d26e58ca-ad02-4444-b8dc-e438791a4b0e)

## CI/CD
![CI-CD](https://github.com/chobo5/salaba/assets/102145785/c90c7a1e-0ad5-43aa-8a91-1eca2507bb31)

## 시스템 아키텍처
![프로젝트 아키텍처](https://github.com/chobo5/salaba/assets/102145785/c6b6b319-545a-4fd6-bf52-85bea843cb44)


## 어플리케이션 아키텍처 - 사라바
![소프트웨어 아키텍처](https://github.com/chobo5/salaba/assets/102145785/866f067a-acfe-4c46-9c52-1fd40ea2f5f6)

## 어플리케이션 아키텍처 - 관리자
![소프트웨어 아키텍처 (2)](https://github.com/chobo5/salaba/assets/102145785/737c82e6-3c44-44ee-a34d-e0e89d31152b)

## ER Diagram
![ERD](https://github.com/chobo5/salaba/assets/102145785/49d57923-528c-49dc-8dae-dc4ce382e911)

### 관리자 페이지

**로그인 페이지**

![로그인페이지](https://github.com/chobo5/salaba/assets/102145785/441ba16d-8cee-4bfa-a951-cb5ea77b782b)


관리자는 크게 관리자와 매니저의 권한으로 나뉩니다
관리자는 열람과 신고/회원의 등급변경/숙소 등록처리 등의 데이터 변경이 가능하지만
매니저 권한의 사용자는 데이터에 대한 열람만 가능하도록 구현하였습니다

**로그인후 메인화면**

![메인화면](https://github.com/chobo5/salaba/assets/102145785/b2aaf910-52f1-4745-8bf6-a1acdbe3e1dc)


관리자는 좌측 사이드바에서 해당 하는 업무를 처리할 수 있습니다.

최상단 우측에는 토큰을 재발급받을 수 있도록 연장기능을 제공하고있습니다

오늘의 할일 파트에서는 처리되지 않은 업무의 양과 해당 업무들만 모아 볼 수 있도록 링크를 제공하고있습니다.

통계 파트에서는 현재 메인앱에 대한 각종 숙소 분포, 가입자 수, 게시물 수, 등급별 회원 수 등의 자료를 확인할 수 있습니다.

**신고 처리**

![신고 처리](https://github.com/chobo5/salaba/assets/102145785/2123ebb1-9652-407c-843e-2243fce38511)


매니저가아닌 관리자의 권한을 갖는 관리자는 신고에 대한 처리가 가능합니다

**회원의 등급 변경**

![회원등급변경](https://github.com/chobo5/salaba/assets/102145785/9d9ed9af-eae8-44f1-b96c-8210a837cf76)


관리자는 회원의 등급을 변경할 수 있습니다.

**회원 검색**

![회원검색](https://github.com/chobo5/salaba/assets/102145785/fdd8be7d-987c-4108-be32-ee9df33b635a)


회원을 이름이나 이메일을 통해 검색할 수 있습니다.

**숙소 심사**
![숙소심사1](https://github.com/chobo5/salaba/assets/102145785/672d91c6-f6a2-488d-b50b-cd3a7d9459df)

![숙소심사2](https://github.com/chobo5/salaba/assets/102145785/04bf2389-60df-420f-ac68-3c847d19c526)


호스트가 등록 신청한 숙소를 심사하며 관리자에서 승인된 숙소는 일반 회원에게 노출됩니다.

**문의 처리**
![문의처리](https://github.com/chobo5/salaba/assets/102145785/d0d01467-d32c-45b4-82db-ec5e62096add)

일반 회원의 문의에 답변할 수 있습니다.

### 메인앱 커뮤니티

**댓글, 답글 작성**

![댓글,답글작성](https://github.com/chobo5/salaba/assets/102145785/a1935561-2740-4834-be5f-b9edda62819c)


**좋아요**

![좋아요](https://github.com/chobo5/salaba/assets/102145785/a2426d9b-72a6-472f-a422-a75622230b56)



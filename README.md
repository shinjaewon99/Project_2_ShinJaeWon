## 🦁 멋쟁이사자처럼 미션형 프로젝트 2️⃣

## 멋사 SNS 

🙋‍♂️ 작성자 : 신재원

💬  Mail : tlswodnjs99@naver.com

> **프로젝트 개발기간: 2023.08.03 ~ 2023.08.08**

---------------------------

## ▶ Environment

- `java 17`
- `JDK 17.0.1`
- Framework : `springboot 3.1.1`
- Database : `sqlite`
- IDE : `Intellij`
- ORM : `JPA`
- Dependency : `jwt` `lombok` `data jpa` `web` `validation` `security` 등

---------------------

### DB ERD 구조
![ERD](https://github.com/shinjaewon99/Daily-Alogorithm/assets/95893341/07898320-8263-4a39-923d-fccaae9785b6)

-------

### 📌요구사항
<details>
<summary>1️⃣ 사용자 인증하기</summary>
<div markdown="1">

1. 사용자 **회원가입**이 가능하다.
    - 회원가입에 필수로 필요한 정보는 아이디와 비밀번호 이다.
    - 부수적으로 이메일, 전화번호를 기입할 수 있다.

2. **아이디와 비밀번호**를 통해 **로그인**이 가능하다.
    - 인증 방식은 JWT를 이용한 토큰 인증 방식을 택한다.

3. **로그인** 한 상태에서, 자신을 대표하는 사진, 프로필 사진을 **업로드** 할 수 있다.

</div>
</details>

<details>
<summary>2️⃣ 피드 구현하기</summary>
<div markdown="1">

1. 피드는 **제목**과 **내용**을 붙일 수 있다.
    - 피드에는 복수의 이미지를 넣을 수 있다.

2. 피드를 작성하고자 한다면 **로그인 된 상태**여야 한다.
    - 사용자가 피드를 작성하면, 특별한 설정 없이 자신이 작성한 피드로 등록된다.

3. 피드는 작성한 사용자 기준으로, **목록 형태의 조회**가 가능하다.
    - 조회를 위해 대상 사용자의 정보가 제공되어야 한다.
    - 피드 목록 조회시, 작성자 아이디, 제목과 **대표 이미지**에 관한 정보가 포함되어야 한다.
    - 이때 대표 이미지란 피드에 등록된 첫번째 이미지를 의미한다.
    - 만약 피드에 등록된 이미지가 없다면, 지정된 기본 이미지를 보여준다.

4. 피드는 **단독 조회**가 가능하다.
    - 피드 단독 조회시, 피드에 연관된 모든 정보가 포함되어야 한다. 이는 등록된 모든 이미지를 확인할 수 있는 각각의 URL과, 댓글 목록, 좋아요의 숫자를 포함한다.
    - 피드를 단독 조회할 시, 로그인이 된 상태여야 한다.

5. 피드는 **수정**이 가능하다.
    - 피드에 등록된 이미지의 경우, 삭제 및 추가만 가능하다.
    - 피드의 이미지가 삭제될 경우 서버에서도 해당 이미지를 삭제하도록 한다.

6. 피드는 **삭제**가 가능하다.
    - 피드가 삭제될때는 실제로 데이터베이스에서 삭제하는 것이 아닌, 삭제 되었다는 표시를 남기도록 한다.

</div>
</details>

<details>
<summary>  3️⃣ 댓글, 좋아요 구현하기</summary>
<div markdown="1">

**댓글 구현**
1. 댓글 작성은 로그인 한 사람만 쓸 수 있다.
    - 댓글에는 작성자 아이디, 댓글 내용이 포함된다.
2. 자신이 작성한 댓글은 수정 및 삭제가 가능하다.
    - 댓글이 삭제될때는 실제로 데이터베이스에서 삭제하는 것이 아닌, 삭제 되었다는 표시를 남기도록 한다.
3. 댓글의 조회는 피드의 단독 조회와 함께 이뤄진다.

**좋아요 구현**
1. 다른 사용자의 피드는 좋아요를 할 수 있다.
    - 자신의 피드의 좋아요는 할 수 없다(권한 없음).
    - 좋아요 요청을 보낼 때 이미 좋아요 한 상태라면, 좋아요는 취소된다

</div>
</details>

<details>
<summary>  4️⃣ 사용자 정보 구현하기</summary>
<div markdown="1">

1. 사용자의 정보는 **조회**가 가능하다.
    - 이때 조회되는 정보는 아이디와 프로필 사진이다.

2. 로그인 한 사용자는 **다른 사용자를 팔로우** 할 수 있다.
    - 팔로우는 일방적 관계이다. A 사용자가 B를 팔로우 하는 것이 B 사용자가 A를 팔로우 하는것을 의미하지 않는다.

3. 로그인 한 사용자는 팔로우 한 사용자의 **팔로우를 해제**할 수 있다.

4. **로그인** 한 사용자는 다른 사용자와 **친구 관계**를 맺을 수 있다.
    - 친구 관계는 양방적 관계이다. A 사용자가 B와 친구라면, B 사용자와 A 도 친구이다.
    - A 사용자는 B 사용자에게 친구 요청을 보낸다.
    - B 사용자는 자신의 친구 요청 목록을 확인할 수 있다.
    - B 사용자는 친구 요청을 수락 혹은 거절할 수 있다.

5. 사용자의 **팔로우**한 모든 사용자의 **피드 목록을 조회**할 수 있다.
    - 이때 작성한 사용자와 무관하게 작성된 순서의 역순으로 조회한다.
    - 그 외 조회되는 데이터는 피드 목록 조회와 동일하다.

6. 사용자와 **친구관계**의 모든 사용자의 **피드 목록을 조회**할 수 있다.
    - 이때 작성한 사용자와 무관하게 작성된 순서의 역순으로 조회한다.
    - 그 외 조회되는 데이터는 피드 목록 조회와 동일하다.

</div>
</details>

### 📌REST API
<details>
<summary>1️⃣ 사용자 인증과 프로필</summary>
<div markdown="1">

**회원가입**<br>
POST `/api/user/join` <br>

**로그인**<br>
POST `/api/user/login` <br>

**프로필 이미지 등록**<br>
POST `/api/user/{userId}/postProfile` <br>

</div>
</details>

<details>
<summary>2️⃣ 피드 구현</summary>
<div markdown="1">

**피드 작성**<br>
POST `api/user/{userId}/feed` <br>

**피드 이미지 등록 작성**<br>
POST `api/user/{userId}/feed/{feedId}/postImg` <br>


**피드 전체 조회**<br>
GET `api/user/{userId}/feed/{feedId}/readLists` <br>

**피드 단독 조회**<br>
GET `api/user/{userId}/feed/{feedId}` <br>

**피드 논리 삭제**<br>
DELETE `api/user/{userId}/feed/{feedId}` <br>


</div>
</details>

<details>
<summary>3️⃣ 댓글, 좋아요 구현</summary>
<div markdown="1">

**댓글 작성**<br>
POST `api/user/{userId}/feed/{feedId}/comment` <br>

**댓글 수정**<br>
PUT `api/user/{userId}/feed/{feedId}/comment/{commentId}` <br>

**댓글 논리 삭제**<br>
DELETE `api/user/{userId}/feed/{feedId}/comment/{commentId}` <br>

**좋아요 요청과 취소**<br>
POST `api/user/{userId}/feed/{feedId}/like` <br>
</div>
</details>


<details>
<summary>4️⃣ 사용자 정보 구현</summary>
<div markdown="1">

**사용자 정보 조회**<br>
GET `/api/user/{userId}` <br>
</div>
</details>
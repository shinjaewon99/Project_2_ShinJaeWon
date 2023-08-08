## 🦁 멋쟁이사자처럼 미션형 프로젝트 2️⃣

## 멋사 SNS 🌈

🙋‍♂️ 작성자 : 신재원

💬  Mail : tlswodnjs99@naver.com

> **프로젝트 개발기간: 2023.08.03 ~ 2023.08.08**

---------------------------

## ▶ Environment
> Language : Java 17

> IDE : Intellij

> Framework : SpringBoot (3.1.1)

➡ SpringSecurity 6.0

➡ Spring Data Jpa

> DataBase : SqlLite

> ORM : Hibernate

> Jwt

---------------------

### DB ERD 구조
![10](https://github.com/likelion-backend-5th/Project_2_ShinJaeWon/assets/95893341/6d84da95-3947-4149-a6e6-da0ca853808b)



-------------
-------

## 📢 프로젝트 요구사항
### 1️⃣ DAY 1/ 사용자 인증하기
🛠 개발 기간 08.03 ~ 08.04 
<details>
<summary> 1. 사용자 회원가입이 가능하다. </summary>
<div markdown="1">
<br>
<ul>
  <li>회원가입에 필수로 필요한 정보는 아이디와 비밀번호 이다.</li><br>
  <li>부수적으로 이메일, 전화번호를 기입할 수 있다.</li><br>
</ul>
  </div>
</details>

#### 📍 사용자 회원가입 API : https://documenter.getpostman.com/view/22851675/2s9XxySDrY

<details>
<summary> 2. 아이디와 비밀번호를 통해 로그인이 가능하다. </summary>
<div markdown="1">
<br>
<ul>
  <li>인증 방식은 JWT를 이용한 토큰 인증 방식을 택한다.</li><br>
</ul>
  </div>
</details>

#### 📍 회원가입한 사용자 로그인 API : https://documenter.getpostman.com/view/22851675/2s9XxySDrb


<summary> 3. 로그인 한 상태에서, 자신을 대표하는 사진, 프로필 사진을 업로드 할 수 있다.</summary>

#### 📍 로그인한 사용자 프로필 사진 등록 API : https://documenter.getpostman.com/view/22851675/2s9XxySDrd
- #### 로그인한 사용자는 pathvariable 어노테이션을 통해 등록된 회원이 repository에 존재하는지 검증, 예외처리 하였습니다.

---------------------
## ❓ 1일차 미션후 배운점 
전역으로 예외처리를 핸들링하는 클래스를 만들어 1일차 미션의 요구사항에 대한 예외처리를 핸들링 했습니다.

(EX : 회원가입, 로그인시 아이디 혹은 비밀번호를 입력하지 않은경우, 회원으로 등록되지 않은 사용자가 프로필 사진 등록을 시도할 경우 등)

--------------------------------------------------------------------
--------------------------------------------------------

### 2️⃣ DAY 2/ 피드 구현하기
🛠 개발 기간 08.05 ~ 08.06 
<details>
<summary> 1. 로그인 상태에서 피드에 제목과 내용을 붙일수 있습니다. </summary>
<div markdown="1">
<br>
<ul>
  <li>피드에는 복수의 이미지를 넣을 수 있다.</li><br>
  <li>제목과 내용은 필수입력으로 설정 하였습니다.</li><br>
</ul>
  </div>
</details>

#### ✈로그인한 사용자 피드 등록, 등록된 피드에 이미지 등록 API : https://documenter.getpostman.com/view/22851675/2s9XxztYBk
- #### 로그인한 사용자는 pathvariable 어노테이션을 통해 등록된 회원이 repository에 존재하는지 검증, 예외처리 하였습니다.
- #### 피드가 등록되어있는지는 pathvariable 어노테이션을 통해 피드의 PK 값이 repository에 존재하는지 검증, 예외처리 하였습니다.

<details>
<summary> 2. 피드는 작성한 사용자 기준으로, 목록 형태의 조회가 가능하다.</summary>
<div markdown="1">
<br>
<ul>
  <li>조회를 위해 대상 사용자의 정보가 제공되어야 한다.</li><br>
  <li>피드 목록 조회시, 작성자 아이디, 제목과 대표 이미지에 관한 정보가 포함되어야 합니다.</li><br>
  <li>이때 대표 이미지란 피드에 등록된 첫번째 이미지를 의미합니다.</li><br>
  <li>만약 피드에 등록된 이미지가 없다면, 지정된 기본 이미지를 보여준다.</li><br>
</ul>
  </div>
</details>

#### ✈ 피드 목록형태 조회 API : https://documenter.getpostman.com/view/22851675/2s9Xxzusov
- #### 요구사항에 맞게 피드에 등록된 이미지가 있을경우 대표이미지 (첫번째) 이미지의 정보를 반환하며, 이미지가 없을경우 기본 (static) 이미지를 보여주게 했습니다.

<details>
<summary> 3. 피드는 단독 조회가 가능하다.</summary>
<div markdown="1">
<br>
<ul>
  <li>피드 단독 조회시, 피드에 연관된 모든 정보가 포함되어야 한다. 이는 등록된 모든 이미지를 확인할 수 있는 각각의 URL과, 댓글 목록, 좋아요의 숫자를 포함합니다.</li><br>
  <li>피드를 단독 조회할 시, 로그인이 된 상태여야 합니다.</li><br>
</ul>
  </div>
</details>

#### ✈ 피드 단독 조회 API : https://documenter.getpostman.com/view/22851675/2s9Xxzts4L
- #### 로그인한 사용자는 pathvariable 어노테이션을 통해 회원의 PK 값이 repository에 존재하는지 검증, 예외처리 하였습니다.
- #### 피드가 등록되어있는지는 pathvariable 어노테이션을 통해 피드의 PK 값이 repository에 존재하는지 검증, 예외처리 하였습니다.


<details>
<summary> 4. 피드는 수정이 가능하다.</summary>
<div markdown="1">
<br>
<ul>
  <li>피드에 등록된 이미지의 경우, 삭제 및 추가만 가능하다.</li><br>
  <li>피드의 이미지가 삭제될 경우 서버에서도 해당 이미지를 삭제하도록 한다.</li><br>
</ul>
  </div>
</details>


<details>
<summary> 5. 피드는 삭제가 가능하다.</summary>
<div markdown="1">
<br>
<ul>
  <li>피드가 삭제될때는 실제로 데이터베이스에서 삭제하는 것이 아닌, 삭제 되었다는 표시를 남기도록 한다.</li><br>
</ul>
  </div>
</details>
이러한 삭제를 논리삭제, softDelete 라고 한다고 합니다. "delted_at" 컬럼으로 표시를 남겼습니다. 

피드를 등록했을경우 데이터베이스 
> ![11](https://github.com/likelion-backend-5th/Project_2_ShinJaeWon/assets/95893341/94a1ca5e-c333-42c7-bc4b-c8b12f34e407)

피드를 논리 삭제했을경우 데이터베이스
> ![12](https://github.com/likelion-backend-5th/Project_2_ShinJaeWon/assets/95893341/6a58b5c6-8182-433a-a62d-8b8a78cc8db4)

❗ 데이터베이스가 삭제되지않고 컬럼을 통해 논리적인 삭제가 된것을 알수있습니다.

#### ✈ 피드 논리삭제 조회 API : https://documenter.getpostman.com/view/22851675/2s9Xxzts4R

-------------
## ❓ 2일차 미션후 배운점
테이블의 연관관계에 따라 조회하고 호출하는점이 어려웠고 부족했던것 같습니다. 

이번 2일차 미션을 통해 로직을 작성하며 객체의 일관성 및 연관관계를 설정하는데 있어 조금이라도 성장 한것같습니다.

------------------------------
-------------------------------------------
### 3️⃣ DAY 3/ 댓글, 좋아요 구현하기
🛠 개발 기간 08.06 ~ 08.07

### 📢 댓글 구현하기 
<details>
<summary> 1. 댓글 작성은 로그인 한 사람만 쓸 수 있다.</summary>
<div markdown="1">
<br>
<ul>
  <li>댓글에는 작성자 아이디, 댓글 내용이 포함된다.</li><br>
</ul>
  </div>
</details>

#### 📌댓글 작성 API : https://documenter.getpostman.com/view/22851675/2s9XxztsDJ

<details>
<summary> 2. 자신이 작성한 댓글은 수정 및 삭제가 가능하다.</summary>
<div markdown="1">
<br>
<ul>
  <li>댓글이 삭제될때는 실제로 데이터베이스에서 삭제하는 것이 아닌, 삭제 되었다는 표시를 남기도록 한다.</li><br>
</ul>
  </div>
</details>

#### 📌댓글 수정, 삭제 API : https://documenter.getpostman.com/view/22851675/2s9XxztsHa

이러한 삭제를 논리삭제, softDelete 라고 한다고 합니다. "delted_at" 컬럼으로 표시를 남겼습니다. 

<summary> 3. 댓글의 조회는 피드의 단독 조회와 함께 이뤄진다.</summary>


## 🚫 주의 

좋아요 구현하기 API 테스트를 하기위해서는 회원가입 API 에서 회원 2 명을 생성하기 위해 회원가입 요청을 2 번 진행해야 합니다.

(자신의 피드에 좋아요를 막는것을 확인할수 있게 하기 위함)

### 📢 좋아요 구현하기

<details>
<summary> 1. 다른 사용자의 피드는 좋아요를 할 수 있다.</summary>
<div markdown="1">
<br>
<ul>
  <li>자신의 피드의 좋아요는 할 수 없다(권한 없음).</li><br>
  <li>좋아요 요청을 보낼 때 이미 좋아요 한 상태라면, 좋아요는 취소된다.</li><br>
</ul>
  </div>
</details>

#### 📌피드 좋아요 구현 API : https://documenter.getpostman.com/view/22851675/2s9XxztsHg



## ❓ 3일차 미션후 배운점
이미 좋아요가 눌러져있는경우 기존의 좋아요를 취소하는 요구사항이였습니다.

기존의 좋아요를 취소 (삭제) 시도시 쿼리문은 select 문을 통해 엔티티를 얻어온후 delete 쿼리를 시도하는 (보내는) 것을 배웠습니다.

delete 메소드가 동작하지않아 원인을 찾아보면서 hibernate에서 동작하는 SQL 순서가 정해져 있다는 것을 배웠습니다.

### ❗ 3일차 좋아요 기능 Issue
> ![10](https://github.com/likelion-backend-5th/Project_2_ShinJaeWon/assets/95893341/78d78239-7fae-48d4-8329-7d982b293c26)
> 
flush() 메소드를 활용하여 변경된 데이터를 데이터베이스에 반영하였는데도 delete쿼리가 나가지 않아 데이터베이스에서 삭제가 안되는 이슈가 있습니다.


### 4️⃣ DAY 4/ 사용자 정보 구현하기
🛠 개발 기간 08.07 ~ 08.08

<details>
<summary> 1. 사용자의 정보는 조회가 가능하다.</summary>
<div markdown="1">
<br>
<ul>
  <li>이때 조회되는 정보는 아이디와 프로필 사진이다.</li><br>
</ul>
  </div>
</details>

#### 🤛 사용자 정보 조회 API : https://documenter.getpostman.com/view/22851675/2s9XxzuYdY

<details>
<summary> 2. 로그인 한 사용자는 다른 사용자를 팔로우 할 수 있다.</summary>
<div markdown="1">
<br>
<ul>
  <li>팔로우는 일방적 관계이다. A 사용자가 B를 팔로우 하는 것이 B 사용자가 A를 팔로우 하는것을 의미하지 않는다.</li><br>
</ul>
  </div>
</details>


<summary> 3. 로그인 한 사용자는 팔로우 한 사용자의 팔로우를 해제할 수 있다.</summary>


<details>
<summary> 4. 로그인 한 사용자는 다른 사용자와 친구 관계를 맺을 수 있다.</summary>
<div markdown="1">
<br>
<ul>
  <li>친구 관계는 양방적 관계이다. A 사용자가 B와 친구라면, B 사용자와 A 도 친구이다.</li><br>
  <li>A 사용자는 B 사용자에게 친구 요청을 보낸다.</li><br>
  <li> B 사용자는 자신의 친구 요청 목록을 확인할 수 있다.</li><br>
  <li> B 사용자는 친구 요청을 수락 혹은 거절할 수 있다.</li><br>
</ul>
  </div>
</details>


<details>
<summary> 5. 사용자의 팔로우한 모든 사용자의 피드 목록을 조회할 수 있다.</summary>
<div markdown="1">
<br>
<ul>
  <li>이때 작성한 사용자와 무관하게 작성된 순서의 역순으로 조회한다.</li><br>
  <li>그 외 조회되는 데이터는 피드 목록 조회와 동일하다.</li><br>
</ul>
  </div>
</details>

<details>
<summary> 6. 사용자와 친구관계의 모든 사용자의 피드 목록을 조회할 수 있다.</summary>
<div markdown="1">
<br>
<ul>
  <li>이때 작성한 사용자와 무관하게 작성된 순서의 역순으로 조회한다.</li><br>
  <li>- 그 외 조회되는 데이터는 피드 목록 조회와 동일하다.</li><br>
</ul>
  </div>
</details>


## ❓ 4일차 미션후 배운점
User의 관해 팔로우와 팔로윙 관계의 비즈니스 로직을 작성하는게 많이 어려웠던것 같다. 요구사항에 주어진 ERD를 보며 N : N 관계로 풀어야되는지, 1 : N 관계로 풀어야되는지 경험을 쌓을수 있었습니다.

얽혀진 DB테이블의 대해 연관관계 설정을 배울수 있었고, 데이터베이스의 일관성을 유지 해야 하는 점을 배웠습니다.


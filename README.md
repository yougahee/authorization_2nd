# Authorization Web site
- 로그인, 회원가입을 통해 사용자의 정보를 DB에 저장한다.
- Token과 Refresh Token을 활용하여 세션을 유지하고 사용자 본인임을 확인하는 용도로 사용한다. 

## Front
- Vue.js
- axios

## Server
- Spring Boot
- JPA
- JWT ( JSON Web Token)
- Email인증

</br>
</br>

# DB 
- user table

| Column | type |  설명 | PK/FK |
| :----: | :------: | :----------------------: | :----: |
| user_idx | BIGINT | index값 ( 자동 생성 ) | PK |
| email | VARCHAR | user ID(이메일형식) |  |
| password | VARCHAR | user 비밀번호 |  |
| salt | VARCHAR | user 비밀번호 Salt |  |
| name | VARCHAR | user 이름 |  |
| gender | VARCHAR | user 성별 |  |
| refresh_token | VARCHAR | refresh_token |  |
| create_time | Date | 생성날짜 |  |


</br>
</br>


# dependencies
```
dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-devtools'

	//db
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'mysql:mysql-connector-java'


	//lombok
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	implementation 'org.springframework.boot:spring-boot-starter-test'

	//encryption
	implementation 'org.springframework.security:spring-security-crypto:5.1.5.RELEASE'

	//valid
	implementation 'org.springframework.boot:spring-boot-starter-validation:2.3.3.RELEASE'

	//jwt
	implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.7.0'


	//email
	implementation 'org.springframework.boot:spring-boot-starter-mail'
}
```


</br>
</br>

# 적용

## JWT(JSON Web Token)
- header, payload, signature로 나누어진다.
- token, refresh token

</br>

## Token이 필요한 이유?
- Access Token을 사용하는 이유는 사용자를 인증하는 방식이다. 예를 들어 마이페이지에 접근한다고 생각해보면, '나'라는 것이 증명되어야 한다. 
- '나'라는 것을 어떻게 증명할 수 있을까?
  - 증명이 필요한 요청마다 로그인을 할 수도 있겠다.
  - 하지만, 매번 로그인을 해서 증명하는 것은 너무나 번거로운 일이다. 그렇기 때문에, 로그인을 했을 때, token을 발급받아서 클라이언트에서 token('나'를 증명해줄 증명서)을 저장하여 인증이 필요한 요청 header에 함께 보낸다. 

</br>

## 그렇다면 RefreshToken은 왜 사용하는가?
- 클라이언트와 서버사이에서 정보를 주고받을 때, Token을 활용하는데, HTTP로 보내지는 과정에서 갈취를 당할 수도 있다. 
- 그렇기 때문에 Access Token의 유지시간을 짧게 주고 Refresh Token을 사용한다. 

</br>

## 일반적으로 RefreshToken은 회원 DB에 저장한다고 한다. 왜일까?
- refresh token으로 access token을 재발급 받을 때, refresh token이 서버가 발급한 정상적인 토큰인지 다시 한번 검증하기 위한 것

</br>

## 구현한 방법
1. 사용자가 로그인을 했을 때, access token과 refresh token을 발급해준다.  
	- access token의 만료시간은 1시간, refresh token의 만료시간은 1주일로 설정했다.
2. 로그인을 한 유저는 header에 token을 담아서 본인임을 인증한다.
3. 만약, token이 만료가 된다면 서버에서는 만료된 토큰이라는 응답을 보낸다.
4. 클라이언트에서 refresh token을 보내고 자신임을 증명하고 서버는 다시 access token과 refresh token을 발급해준다.

</br>

## 또 다른 생각
1. 만약, refresh token도 갈취를 당하면 어떻게 하나?
	- refresh token도 새로 발급은 할 수 있지만, 로컬에 담겨있다는 점, header 또는 body에 담겨서 서버로 보내진다는 점을 두고 봤을 때, 갈취를 당했을 경우를 대비해야한다. 
	- 요청하는 IP 주소나 다른 정보들을 함께 저장해두어 refresh token을 새로 발급해준다던가 하는 방법이 있을 것 같다.
	- access token이 만료되서 refresh token을 보내는 과정이 상당히 복잡하여 DB에 저장해놓은 refresh token을 어떻게 잘 활용할 수 있을까? 
		- 내가 구현한 대로 문제를 해결하면 속도와 성능이 느려질 가능성이 꽤 커보인다. 


</br>
</br>


# 비밀번호 암호화
- SHA-256
- salt
- BCryptPasswordEncoder

</br>

### BCryptPasswordEncoder?
- 비밀번호 단방향 해시 알고리즘 중 하나
- 암호화시킬 text + salt를 더하여 digest를 만드는 것이다. 

### 그외
1. PBKDF2(Password-Based Key Derivation Function)
2. scrypt

### SHA-256이란?
- Secure Hash Algorithm의 약자로 해시함수를 사용하는 알고리즘이다. 

### MD5란?
- MD5(Message-Digest algorithm 5)는 128비트 암호화 해시 함수이다.
- 심한 암호화 결함이 있기 때문에 보안관련 용도로는 권장하지 않고 있으며 보통 프로그램이나 파일이 원본 그대로인지 확인하는 무결성 검사등에 활용된다. 

### 대부분 SHA-256을 더 사용하고 MD5는 쓰는 것을 잘 보지 못했다. 그 이유는?
- 현재 MD5 알고리즘을 보안 관련 용도로 쓰는 것을 권장하지 않고 있으며, 심각한 보안 문제를 야기할 수 도 있다고 한다. ( SSL 인증서를 변조하는 것이 가능하다는 것이 발표되었다. )

</br>


# Error Log
1. BCrypto를 사용하기 위해 dependecies에 implementation 'org.springframework.boot:spring-boot-starter-security' 을 추가했다.
  	- 이것을 추가하고 POSTMAN으로 테스트를 해보니 spring security가 생겨서 401 에러가 발생했고, web으로 url을 입력해보니, 로그인을 하라는 창이 나왔다.
  	- 원인 : 추가한 dependency는 spring security를 활성화시켜주었다.
  	- 해결방법 : implementation 'org.springframework.security:spring-security-crypto:5.1.5.RELEASE' 대체

2. E-mail 전송하기 위해 사용한 JavaMailSender가 계속해서 NULL값이 들어왔다.
  	- 기본 생성자 주입을 해주면 되는데, @AutoWired를 썼더니 순환호출로 인해 메모리가 터져서 컴퓨터가 다운됐다.
	- @RequiredArgsConstructor 을 사용하자!
		- 이것을 사용할 때는 생성자 주입이 될 대상을 private final로 적어주어야 한다.

3. properties에서 gmail로 메일을 보내기 위한 설정도 다 해놓았고, JavaMailSender에도 NULL값이 들어가지 않는것도 확인했는데, MailSendException이 발생하였다.
	- 에러메시지를 보니 SSL, SSLHandShakeException, certification등 보안관련한 용어들이 나왔다.
	- 구글링 끝에 보안 프로그램을 끄고 돌려보니 메일이 잘 온 것을 확인할 수 있었다. 

</br>

# 구현요소

## 1.signup
<img src = "https://github.com/yougahee/authorization_2nd/blob/master/imgs/SignUpPage.PNG">


## 2-1. login success
<img src = "https://github.com/yougahee/authorization_2nd/blob/master/imgs/LoginPage.PNG">


## 2-2 login fail - alert
<img src = "https://github.com/yougahee/authorization_2nd/blob/master/imgs/Login_fail.PNG">


## 3. 아이디 중복 체크
<img src = "https://github.com/yougahee/authorization_2nd/blob/master/imgs/exist_id.PNG">


## 4. 임시비밀번호를 메일로 발송
<img src = "https://github.com/yougahee/authorization_2nd/blob/master/imgs/send_mail_success.PNG">

## 5. 회원 리스트 뿌려주기
<img src = "https://github.com/yougahee/authorization_2nd/blob/master/imgs/getAllUserList.PNG" >
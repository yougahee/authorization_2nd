# 프로젝트 소개
- Authorization Web site
- 간단한 로그인, 회원가입을 통해 사용자의 정보를 DB에 저장할  수 있다.
- 로그인에서 Token과 Refresh Token을 활용하여 세션을 유지하고 사용자 본인임을 확인하는 용도로 사용한다. 

## Front
- Vue.js
- axios

## Server
- Spring Boot
- JPA
- JWT


## jwt
- token, refresh token

## 비밀번호 암호화
- SHA-256
- salt
- BCryptPasswordEncoder


## Encryption

### BCryptPasswordEncoder?

### SHA-256이란?
- Secure Hash Algorithm의 약자로 해쉬함수를 사용하는 알고리즘이다. 

### MD5란?

### 대부분 SHA-256을 더 사용하고 MD5는 쓰는 것을 잘 보지 못했다. 그 이유는?

</br>


# Error Log
1. BCrypto를 사용하기 위해 dependecies에 implementation 'org.springframework.boot:spring-boot-starter-security' 을 추가했다.
  - 이것을 추가하고 POSTMAN으로 테스트를 해보니 spring security가 생겨서 401 에러가 발생했고, web으로 url을 입력해보니, 로그인을 하라는 창이 나왔다.
  - 원인 : 추가한 dependency는 spring security를 활성화시켜주었다.
  - 해결방법 : implementation 'org.springframework.security:spring-security-crypto:5.1.5.RELEASE' 대체


</br>

# 구현요소


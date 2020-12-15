# 프로젝트 소개
- URL을 입력하면, URL의 길이를 줄여주고 Redirect까지 할 수 있도록 하는 간단한 웹사이트
- 참고 웹사이트 : https://bitly.com/

## Front
- Vue.js
- axios

## Server
- Spring Boot
- JPA

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


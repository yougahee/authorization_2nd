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

## DB 구조


</br>

## JWT(JSON Web Token)
- header, payload, signature로 나누어진다.
- token, refresh token

### Token이 필요한 이유?
- Access Token을 사용하는 이유는 사용자를 인증하는 방식이다. 예를 들어 마이페이지에 접근한다고 생각해보면, '나'라는 것이 증명되어야 한다. 
- '나'라는 것을 어떻게 증명할 수 있을까?
  - 증명이 필요한 요청마다 로그인을 할 수도 있겠다.
  - 하지만, 매번 로그인을 해서 증명하는 것은 너무나 번거로운 일이다. 그렇기 때문에, 로그인을 했을 때, token을 발급받아서 클라이언트에서 token('나'를 증명해줄 증명서)을 저장하여 인증이 필요한 요청 header에 함께 보낸다. 

### 그렇다면 RefreshToken은 왜 사용하는가?
- 클라이언트와 서버사이에서 정보를 주고받을 때, Token을 활용하는데, HTTP로 보내지는 과정에서 갈취를 당할 수도 있다. 
- 그렇기 때문에 Access Token의 유지시간을 짧게 주고 Refresh Token을 사용한다. 

### 일반적으로 RefreshToken은 회원 DB에 저장한다고 한다. 왜일까?
- refresh token으로 access token을 재발급 받을 때, refresh token이 서버가 발급한 정상적인 토큰인지 다시 한번 검증하기 위한 것

</br>

## 비밀번호 암호화
- SHA-256
- salt
- BCryptPasswordEncoder

</br>

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


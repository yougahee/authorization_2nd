package com.gaga.auth_server.utils;

import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.TokenResponseDTO;
import com.gaga.auth_server.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Component
public class JwtUtils {

    @Value("${jwt.secret.at}")
    private String ACCESS_SECRET_KEY;

    @Value("${jwt.secret.rt}")
    private String REFRESH_SECRET_KEY;

    //accessToken -> 1hour
    private long accessTokenValidMilisecond = 1000L * 60 * 60;
    //refreshToken -> 1week
    private long refreshTokenValidMilisecond = 1000L * 60 * 60 * 24 * 7;


    //초기화를 수행하는 메서드
    @PostConstruct
    protected void init() {
        ACCESS_SECRET_KEY = Base64.getEncoder().encodeToString(ACCESS_SECRET_KEY.getBytes());
        REFRESH_SECRET_KEY = Base64.getEncoder().encodeToString(REFRESH_SECRET_KEY.getBytes());
    }

    //Access Token 유효한지 파악
    public boolean isValidateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(ACCESS_SECRET_KEY)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            //SignatureException -> token의 형태가 아님
            log.error(e.toString());
            return false;
        }
    }


    //token 발급
    public TokenResponseDTO generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("gender", user.getGender());
        return createToken(claims, user.getEmail());
    }

    //QQ refresh token을 같이 생성하고 하나로 담아서 넘겨주는 것이 나을까?
    //아니면 함수를 따로 분리하는 게 나으려나?
    public TokenResponseDTO createToken(Map<String, Object> claims, String sub) {
        Date now = new Date();
        String accessToken =  Jwts.builder()
                .setClaims(claims) // token에 담을 정보
                .setSubject(sub) //token 제목
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, ACCESS_SECRET_KEY) //알고리즘, 키
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims) // token에 담을 정보
                .setSubject(sub) //token 제목
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, REFRESH_SECRET_KEY) //알고리즘, 키
                .compact();

        return new TokenResponseDTO(accessToken, refreshToken);
    }
}

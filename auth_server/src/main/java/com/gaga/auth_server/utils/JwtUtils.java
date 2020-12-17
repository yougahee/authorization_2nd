package com.gaga.auth_server.utils;

import com.gaga.auth_server.AuthControllerAdvice;
import com.gaga.auth_server.dto.response.TokenResponseDTO;
import com.gaga.auth_server.exception.UnauthorizedException;
import com.gaga.auth_server.model.User;
import com.mysql.cj.exceptions.UnableToConnectException;
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
    public void isValidateToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(ACCESS_SECRET_KEY)
                    .parseClaimsJws(token);

            boolean isValid = !claims.getBody().getExpiration().before(new Date());
            if(!isValid) throw new UnauthorizedException();
        } catch (Exception e) {
            log.error(e.toString());
            throw new UnauthorizedException();
            /*
            ##
            여러가지 경우 exception이 나온다.
            SignatureException ->
            ExpriedJwtException ->
            등등 ..
            더 찾아보기 그리고 원래는 이런 에러? 들을 따로 나눠서 관리를 하는 것이 맞는 건가?
            */
        }
    }

    public void isValidateRefreshToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(REFRESH_SECRET_KEY)
                    .parseClaimsJws(token);

            boolean isValid = !claims.getBody().getExpiration().before(new Date());
            if(!isValid) throw new UnauthorizedException();
        } catch (Exception e) {
            log.error(e.toString());
            throw new UnauthorizedException();
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

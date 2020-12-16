package com.gaga.auth_server.utils;

import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtils {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    //1시간 유효토큰
    private long tokenValidMilisecond = 1000L * 60 * 60;

    //##postconstruct가 뭐여
    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

   /* public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //토큰이 만료되었는지 확인하기
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //유효한 토큰인지 확인
    public Boolean validToken(String token, UserLogInRequestDTO userLogInRequestDTO) {
        final String username = extractUsername(token);
        return (username.equals(userLogInRequestDTO.getEmail()) && isTokenExpired(token));
    }

    private String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }*/

    //token 발급
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("gender", user.getGender());
        return createToken(claims, user.getName());
    }

    public String createToken(Map<String, Object> claims, String sub) {
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // token에 담을 정보
                .setSubject(sub) //token 제목
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) //알고리즘, 키
                .compact();

    }
}

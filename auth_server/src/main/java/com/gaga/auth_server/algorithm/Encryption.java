package com.gaga.auth_server.algorithm;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Getter
public class Encryption {
    String salt;

    public String encode(String rawPassword) {

        if(rawPassword.length() > 0) {
            salt = BCrypt.gensalt();
            System.out.println("salt : " + salt);
        } else {
            salt = BCrypt.gensalt();
        }

        return BCrypt.hashpw(rawPassword, salt);
    }

    public String encodeWithSalt(String rawPassword, String salt) {
        return BCrypt.hashpw(rawPassword, salt);
    }
}

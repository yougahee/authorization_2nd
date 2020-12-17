package com.gaga.auth_server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class LoginTokenResponseDTO {
    private int status;
    private boolean success;
    private String message;
    private String token;
    private String refreshToken;

    public LoginTokenResponseDTO(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }

    public LoginTokenResponseDTO(int status, boolean success, String message, String token, String refreshToken) {
        this.status = status;
        this.success = success;
        this.message = message;
        this.token = token;
        this.refreshToken = refreshToken;
    }
}

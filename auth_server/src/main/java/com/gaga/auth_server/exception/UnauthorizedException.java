package com.gaga.auth_server.exception;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 1000L;

    public UnauthorizedException() {
        super("유효하지 않은 토큰입니다.");
    }
}

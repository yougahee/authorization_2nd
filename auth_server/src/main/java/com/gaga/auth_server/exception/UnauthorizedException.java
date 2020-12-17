package com.gaga.auth_server.exception;

import lombok.Getter;

@Getter
public class UnauthorizedException extends RuntimeException {
    static final long serialVersionUID = 1000L;
    public final String errorMessage = "유효하지 않은 값입니다.";
}

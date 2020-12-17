package com.gaga.auth_server.exception;

import lombok.Getter;

@Getter
public class NoExistEmailException extends RuntimeException {
    public final String errorMessage = "존재하지 않은 회원입니다.";
}
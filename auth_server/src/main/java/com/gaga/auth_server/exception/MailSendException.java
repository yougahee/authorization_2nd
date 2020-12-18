package com.gaga.auth_server.exception;

import lombok.Getter;

@Getter
public class MailSendException extends RuntimeException{
    public final String errorMessage = "메일발송에 실패했습니다.";
}

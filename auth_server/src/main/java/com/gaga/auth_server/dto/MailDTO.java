package com.gaga.auth_server.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MailDTO {
    private String address;
    private String title;
    private String message;

    public MailDTO(String address) {
        this.address = address;
        this.title = "Gahui Place에서 임시비밀번호를 전송해드립니다.";
    }
}

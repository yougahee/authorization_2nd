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
}

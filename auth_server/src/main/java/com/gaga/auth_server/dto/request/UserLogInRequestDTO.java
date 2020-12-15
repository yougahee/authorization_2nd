package com.gaga.auth_server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLogInRequestDTO {
    private String email;
    private String password;
}

package com.gaga.auth_server.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoRequestDTO {
    private String email;
    private String password;
    private String name;
    private String gender;
}

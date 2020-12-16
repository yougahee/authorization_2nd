package com.gaga.auth_server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class GetAllUsersResponseDTO {
    private String email;
    private String name;

    public GetAllUsersResponseDTO(String email, String name) {
        this.email = email;
        this.name = name;
    }
}

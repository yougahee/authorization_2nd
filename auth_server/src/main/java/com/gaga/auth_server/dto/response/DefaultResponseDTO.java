package com.gaga.auth_server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class DefaultResponseDTO {
    private int status;
    private boolean success;
    private String message;

    public DefaultResponseDTO(int status, boolean success, String message) {
        this.status = status;
        this.success = success;
        this.message = message;
    }
}

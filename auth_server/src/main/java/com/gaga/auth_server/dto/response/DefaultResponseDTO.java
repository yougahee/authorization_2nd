package com.gaga.auth_server.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class DefaultResponseDTO {
    private StatusEnum status;
    private boolean success;
    private String message;
    private Object data;

    public DefaultResponseDTO() {
        this.status = StatusEnum.BAD_REQUEST;
        this.data = null;
        this.success = false;
        this.message = null;
    }

    public DefaultResponseDTO(String message) {
        this.status = StatusEnum.OK;
        this.success = true;
        this.message = message;
    }
}

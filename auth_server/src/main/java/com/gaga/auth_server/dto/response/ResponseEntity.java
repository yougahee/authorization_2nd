package com.gaga.auth_server.dto.response;

import com.sun.istack.Nullable;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;

public class ResponseEntity<T> extends HttpEntity<T> {

    private final HttpStatus status;

    public ResponseEntity(@Nullable T body,
                          @Nullable MultiValueMap<String, String> headers,
                          HttpStatus status) {

        super(body, headers);
        Assert.notNull(status, "HTTPStatus must not be null");
        this.status = status;

    }
}

package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.request.UserEmailIdRequestDTO;
import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.dto.response.ResponseEntity;
import com.gaga.auth_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SignUpController {
    UserService userService;
    DefaultResponseDTO defaultResponseDTO;
    HttpHeaders headers;

    @PostConstruct
    protected void init() {
        headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    }

    @CrossOrigin
    @GetMapping("/")
    public String test() {
        return "test";
    }


}

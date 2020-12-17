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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.nio.charset.Charset;

@Slf4j
@RequiredArgsConstructor
@RestController("/signup")
public class SignUpController {
    private final UserService userService;
    DefaultResponseDTO defaultResponseDTO;
    HttpHeaders headers;

    @PostConstruct
    protected void init() {
        headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    }

    @CrossOrigin
    @PostMapping("")
    public DefaultResponseDTO signUp(@Valid @RequestBody UserInfoRequestDTO userInfo) {
        log.info("signup : ");
        return userService.insertUser(userInfo);
    }

    @CrossOrigin
    @PostMapping("/check_id")
    public ResponseEntity<DefaultResponseDTO> checkId(@RequestBody UserEmailIdRequestDTO userInfo) {
        if(userService.checkId(userInfo.getEmail())) {
            defaultResponseDTO = new DefaultResponseDTO("사용가능한 아이디입니다.");
        }else {
            defaultResponseDTO = new DefaultResponseDTO("이미 사용하고 있는 아이디입니다.");
        }

        return new ResponseEntity<>(defaultResponseDTO, headers, HttpStatus.OK);
    }
}

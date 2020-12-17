package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.*;
import com.gaga.auth_server.service.UserService;
import com.gaga.auth_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    DefaultResponseDTO defaultResponseDTO;
    HttpHeaders headers;

    @PostConstruct
    protected void init() {
        //##이거 지워야 함
        defaultResponseDTO = new DefaultResponseDTO();
        headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    }

    @CrossOrigin
    @PostMapping("/login")
    public LoginTokenResponseDTO postLogin(@RequestBody UserLogInRequestDTO loginInfo) {
        return userService.getUserToken(loginInfo);
    }

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<DefaultResponseDTO> getAllUsers(
            @RequestHeader(value = "token") String token) {

        //이러는 경우가 뭐가 있대?
        if(!jwtUtils.isValidateToken(token)) {
            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        List<GetAllUsersResponseDTO> users = userService.getAllUsers();
        //이걸 controller에서 하는 게 맞나? 그리고 성공했을떄, 실패했을때의
        //그런 것들을 여기서 판단하는 게 아니지 않나?
        defaultResponseDTO.setStatus(StatusEnum.OK);
        defaultResponseDTO.setMessage("회원조회 성공");
        defaultResponseDTO.setSuccess(true);
        defaultResponseDTO.setData(users);

        return new ResponseEntity<>(defaultResponseDTO, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> reissueToken(@RequestHeader(value = "refresh_token") String refreshToken) {
        TokenResponseDTO tokenResponseDTO = userService.getReissueToken(refreshToken);
        return new ResponseEntity<>(tokenResponseDTO, headers, HttpStatus.OK);
    }
}

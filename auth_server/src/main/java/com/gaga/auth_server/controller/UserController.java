package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.*;
import com.gaga.auth_server.middleware.AuthMiddleWare;
import com.gaga.auth_server.service.UserService;
import com.gaga.auth_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    DefaultResponseDTO defaultResponseDTO;

    @PostConstruct
    protected void init() {
        defaultResponseDTO = new DefaultResponseDTO();
    }

    @CrossOrigin
    @GetMapping("")
    public String getTest() {
        return "Test";
    }

    @CrossOrigin
    @GetMapping("/users")
    public ResponseEntity<List<GetAllUsersResponseDTO>> getAllUsers(
            @RequestHeader(value = "token") String token) {
        //이것과 같은 서비스에서는 token을 확인해야한다.
        //만약, 유효한 token or refresh token이 왔을 때 반환하는 방법은 어떻게 작성해야할까?
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if(token.equals("") || token.isEmpty()) {
            defaultResponseDTO.setMessage("token을 보내주세요.");
            return new ResponseEntity(defaultResponseDTO, headers, HttpStatus.BAD_GATEWAY);
        }

        if( !jwtUtils.isValidateToken(token)) {
            log.info("token : " + token);
            defaultResponseDTO.setMessage("만료된 토큰입니다.");
            return new ResponseEntity(defaultResponseDTO, headers, HttpStatus.BAD_GATEWAY);
        }

        List<GetAllUsersResponseDTO> users = userService.getAllUsers();

        //이걸 controller에서 하는 게 맞나? 그리고 성공했을떄, 실패했을때의
        //그런 것들을 여기서 판단하는 게 아니지 않나?
        defaultResponseDTO.setStatus(StatusEnum.OK);
        defaultResponseDTO.setMessage("회원조회 성공");
        defaultResponseDTO.setSuccess(true);
        defaultResponseDTO.setData(users);

        return new ResponseEntity(defaultResponseDTO, headers, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/login")
    public LoginTokenResponseDTO postLogin(@RequestBody UserLogInRequestDTO loginInfo) {
        return userService.getUserToken(loginInfo);
    }

    @CrossOrigin
    @PostMapping("/signup")
    public DefaultResponseDTO signUp(@Valid @RequestBody UserInfoRequestDTO userInfo) {
        log.info("signup : ");
        return userService.insertUser(userInfo);
    }
}

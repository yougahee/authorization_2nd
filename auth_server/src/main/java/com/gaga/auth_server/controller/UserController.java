package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.dto.response.GetAllUsersResponseDTO;
import com.gaga.auth_server.dto.response.LoginTokenResponseDTO;
import com.gaga.auth_server.utils.JwtUtils;
import com.gaga.auth_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;
    private final JwtUtils jwtService;

    @CrossOrigin
    @GetMapping("")
    public String getTest() {
        return "Test";
    }

    @CrossOrigin
    @GetMapping("/users")
    public List<GetAllUsersResponseDTO> getAllUsers() {
        return userService.getAllUsers();
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

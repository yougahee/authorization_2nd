package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.dto.response.LoginTokenResponseDTO;
import com.gaga.auth_server.model.User;
import com.gaga.auth_server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @CrossOrigin
    @GetMapping("")
    public String getTest() {
        return "Test";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User>  users = new LinkedList<>();
        return users;
    }

    @CrossOrigin
    @PostMapping("/login")
    public LoginTokenResponseDTO postLogin(@RequestBody UserLogInRequestDTO loginInfo) {
        return userService.getUserToken(loginInfo);
    }

    @CrossOrigin
    @PostMapping("/signup")
    public DefaultResponseDTO signUp(@RequestBody UserInfoRequestDTO userInfo) {
        log.info("signup : ");
        return userService.insertUser(userInfo);
    }
}

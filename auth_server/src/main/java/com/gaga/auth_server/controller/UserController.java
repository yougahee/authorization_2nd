package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.model.User;
import com.gaga.auth_server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public String getTest() {
        return "Test";
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User>  users = new LinkedList<>();
        return users;
    }

/*    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody LoginInfo loginInfo) {

        return
    }*/

    @PostMapping("/signup")
    public DefaultResponseDTO signUp(@RequestBody User userInfo) {
        return userService.insertUser(userInfo);
    }

}

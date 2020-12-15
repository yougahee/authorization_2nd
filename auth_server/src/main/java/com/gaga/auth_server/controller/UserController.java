package com.gaga.auth_server.controller;

import com.gaga.auth_server.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@Controller
public class UserController {

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User>  users = new LinkedList<>();
        return users;
    }

    @GetMapping("")
    public String getTest() {
        return "Test";
    }


}

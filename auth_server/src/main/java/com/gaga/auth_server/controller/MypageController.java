package com.gaga.auth_server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RequestMapping("/mypage")
@RestController
public class MypageController {

    @GetMapping("/")
    public void getMypageInfo() {

    }

    @PutMapping("/point")
    public void updatePoint() {
    }
}

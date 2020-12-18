package com.gaga.auth_server.controller;

import com.gaga.auth_server.dto.request.UserEmailIdRequestDTO;
import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
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
import javax.servlet.http.HttpServletRequest;
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

        jwtUtils.isValidateToken(token);

        List<GetAllUsersResponseDTO> users = userService.getAllUsers();
        //이걸 controller에서 하는 게 맞나? 그리고 성공했을 때, 실패했을때의
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

    @CrossOrigin
    @PostMapping("/find-pw")
    public ResponseEntity<DefaultResponseDTO> findPassword(@Valid @RequestBody UserEmailIdRequestDTO userEmailDTO) {
        DefaultResponseDTO defaultResponseDTO = userService.findPassword(userEmailDTO.getEmail());
        return new ResponseEntity<>(defaultResponseDTO, headers, HttpStatus.OK);
    }

    //이것을 signup으로 옮기면 왜 안돼...? 정말 이해가 안간다!
    @CrossOrigin
    @PostMapping("/signup")
    public DefaultResponseDTO signUp(@Valid @RequestBody UserInfoRequestDTO userInfo) {
        return userService.insertUser(userInfo);
    }

    @CrossOrigin
    @PostMapping("/signup/check-id")
    public ResponseEntity<DefaultResponseDTO> checkId(@RequestBody UserEmailIdRequestDTO userInfo) {
        if(userService.checkId(userInfo.getEmail())) {
            defaultResponseDTO = new DefaultResponseDTO("사용가능한 아이디입니다.");
        }else {
            defaultResponseDTO = new DefaultResponseDTO("이미 사용하고 있는 아이디입니다.");
            defaultResponseDTO.setSuccess(false);
        }

        return new ResponseEntity<>(defaultResponseDTO, headers, HttpStatus.OK);
    }
}

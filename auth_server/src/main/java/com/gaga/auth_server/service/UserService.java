package com.gaga.auth_server.service;

import com.gaga.auth_server.algorithm.Encryption;
import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.dto.response.GetAllUsersResponseDTO;
import com.gaga.auth_server.dto.response.LoginTokenResponseDTO;
import com.gaga.auth_server.model.User;
import com.gaga.auth_server.repository.UserInfoRepository;
import com.gaga.auth_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JwtUtils jwtUtils;

    //QQ 실패했을 때, or 성공했을 때 가지고 있는 틀을 만들어놓고 그것을 사용할 수 있었으면 좋겠다.



    //회원가입
    public DefaultResponseDTO insertUser(UserInfoRequestDTO userInfo) {
        /* ##userInfo의 값이 제대로 들어왔는지 확인해야함.*/

        User user = new User();

        //password 암호화
        Encryption encryption = new Encryption();
        String encryptPW = encryption.encode(userInfo.getPassword());

        //pw, salt를 user에 저장장
        user.setEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setGender(userInfo.getGender());
        user.setPassword(encryptPW);
        user.setSalt(encryption.getSalt());

        //## 만약에 이렇게 사용자의 실제 pwㄹ르 log 찍어버리면 암호화 하는 이유가 없는 느낌이군!
        // 큰일이 생길 것 같은 느낌?
        log.info("암호화한 PW의 값 : " + encryptPW);
        log.info("salt의 값 : " + encryption.getSalt());

        //DB에 저장
        userInfoRepository.save(user);

        //success message를 return 하면 된다.
        return new DefaultResponseDTO(200, true, "회원가입 성공!_!");
    }

    public LoginTokenResponseDTO getUserToken(UserLogInRequestDTO loginInfo) {

        //LoginTokenResponseDTO loginTokenResponseDTO = new LoginTokenResponseDTO();

        //password 암호화
        Encryption encryption = new Encryption();

        //DB에 있는 값과 일치하는지 확인하기.
        log.info("email : " + loginInfo.getEmail());
        User user = userInfoRepository.findByEmail(loginInfo.getEmail());
        String encryptPW = encryption.encodeWithSalt(loginInfo.getPassword(), user.getSalt());
        String dbPW = user.getPassword();
        log.info("dbPW : " + dbPW );
        log.info("encryptPW : " + encryptPW );

        if(dbPW.equals(encryptPW)) {
            String token = jwtUtils.generateToken(user);
            return new LoginTokenResponseDTO(200, true, "로그인 성공!_!", token);
        }
        else {
            return new LoginTokenResponseDTO(400, false, "회원정보와 일치하지 않습니다.");
        }
    }

    public List<GetAllUsersResponseDTO> getAllUsers() {
        List<User> userList = userInfoRepository.findAll();
        List<GetAllUsersResponseDTO> userSimple = new ArrayList<>();

        for(User user : userList) {
            userSimple.add(new GetAllUsersResponseDTO(user.getEmail(), user.getName()));
        }

        return userSimple;
    }

    /*public String encryptPW(String pw) {
        Encryption encryption = new Encryption();
        return encryption.encode(pw);
    }*/

}

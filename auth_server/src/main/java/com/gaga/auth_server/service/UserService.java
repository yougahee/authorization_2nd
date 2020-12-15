package com.gaga.auth_server.service;

import com.gaga.auth_server.algorithm.Encryption;
import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.model.User;
import com.gaga.auth_server.repository.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;

    //QQ 실패했을 때, or 성공했을 때 가지고 있는 틀을 만들어놓고 그것을 사용할 수 있었으면 좋겠다.



    //회원가입
    public DefaultResponseDTO insertUser(User userInfo) {
        /* ##userInfo의 값이 제대로 들어왔는지 확인해야함.*/


        User user = userInfo;

        //password 암호화
        Encryption encryption = new Encryption();
        String encryptPW = encryption.encode(userInfo.getPassword());

        //pw, salt를 user에 저장장
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

}

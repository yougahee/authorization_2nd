package com.gaga.auth_server.service;

import com.gaga.auth_server.algorithm.Encryption;
import com.gaga.auth_server.dto.MailDTO;
import com.gaga.auth_server.dto.request.UserInfoRequestDTO;
import com.gaga.auth_server.dto.request.UserLogInRequestDTO;
import com.gaga.auth_server.dto.response.*;
import com.gaga.auth_server.exception.NoExistEmailException;
import com.gaga.auth_server.model.User;
import com.gaga.auth_server.repository.UserInfoRepository;
import com.gaga.auth_server.utils.CustomMailSender;
import com.gaga.auth_server.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.validation.constraints.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;
    private final JwtUtils jwtUtils;
    private final CustomMailSender customMailSender;

    //QQ 실패했을 때, or 성공했을 때 가지고 있는 틀을 만들어놓고 그것을 사용할 수 있었으면 좋겠다.

    //회원가입
    public DefaultResponseDTO insertUser(UserInfoRequestDTO userInfo) {
        /* ##userInfo의 값이 제대로 들어왔는지 확인해야함.*/
        User user = new User();

        //password 암호화
        Encryption encryption = new Encryption();
        String encryptPW = encryption.encode(userInfo.getPassword());

        log.info("encryptPW" + encryptPW);
        log.info("getSalt" + encryption.getSalt());

        //pw, salt를 user에 저장장
        user.setEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setGender(userInfo.getGender());
        user.setPassword(encryptPW);
        user.setSalt(encryption.getSalt());

        //## 만약에 이렇게 사용자의 실제 pwㄹ르 log 찍어버리면 암호화 하는 이유가 없는 느낌이군!
        // 큰일이 생길 것 같은 느낌? -> 그럴 가능성이 있는 것인가?
        log.info("암호화한 PW의 값 : " + encryptPW);
        log.info("salt의 값 : " + encryption.getSalt());

        //DB에 저장
        userInfoRepository.save(user);

        //success message를 return 하면 된다.
        return new DefaultResponseDTO("회원가입 성공!_!");
    }

    //token
    public LoginTokenResponseDTO getUserToken(UserLogInRequestDTO loginInfo) throws NullPointerException {

        //LoginTokenResponseDTO loginTokenResponseDTO = new LoginTokenResponseDTO();

        //password 암호화
        Encryption encryption = new Encryption();

        //DB에 있는 값과 일치하는지 확인하기.
        User user = userInfoRepository.findByEmail(loginInfo.getEmail());
        String encryptPW = encryption.encodeWithSalt(loginInfo.getPassword(), user.getSalt());
        String dbPW = user.getPassword();
        log.info("dbPW : " + dbPW );
        log.info("encryptPW : " + encryptPW );

        if(dbPW.equals(encryptPW)) {
            String accessToken = jwtUtils.generateToken(user).getAccessToken();
            String refreshToken = jwtUtils.generateToken(user).getAccessToken();
            user.setRefreshToken(refreshToken);
            userInfoRepository.save(user);

            //## 이렇게 쓰는 거 말고 좀 더 깔끔하게 쓸 수 있는 건 없는지 알아보기
            return new LoginTokenResponseDTO(200, true, "로그인 성공!_!", accessToken, refreshToken);
        }
        else {
            return new LoginTokenResponseDTO(400, false, "회원정보와 일치하지 않습니다.");
        }
    }

    public TokenResponseDTO getReissueToken(String refreshToken) {
        TokenResponseDTO tokenResponseDTO = new TokenResponseDTO();

        jwtUtils.isValidateRefreshToken(refreshToken);

        //이것도 이렇게 user를 마음대로 가져와도 되는건가...
        //코드 중복 -> 쪼개야함
        User user = userInfoRepository.findByRefreshToken(refreshToken);

        String at = jwtUtils.generateToken(user).getAccessToken();
        String rt = jwtUtils.generateToken(user).getAccessToken();
        user.setRefreshToken(refreshToken);
        userInfoRepository.save(user);

        tokenResponseDTO.setAccessToken(at);
        tokenResponseDTO.setRefreshToken(rt);
        return tokenResponseDTO;
    }

    public List<GetAllUsersResponseDTO> getAllUsers() {
        List<User> userList = userInfoRepository.findAll();
        List<GetAllUsersResponseDTO> userSimple = new ArrayList<>();

        for(User user : userList) {
            userSimple.add(new GetAllUsersResponseDTO(user.getEmail(), user.getName()));
        }

        return userSimple;
    }

    public boolean checkId(String userId) {
        return !userInfoRepository.existsByEmail(userId);
    }

    public DefaultResponseDTO findPassword(String email) {
        if(checkId(email)) {
            throw new NoExistEmailException();
        }

        User user = userInfoRepository.findByEmail(email);
        log.info("user " + user.getEmail());

        MailDTO mailDTO = new MailDTO(email);
        Encryption encryption = new Encryption();
        //임시 비밀번호 발급하기.
        String tempPW = randomString();
        mailDTO.setMessage(tempPW);

        //DB에도 update
        tempPW = encryption.encode(tempPW);
        user.setPassword(tempPW);
        user.setSalt(encryption.getSalt());
        userInfoRepository.save(user);

        customMailSender.sendMail(mailDTO);
        return new DefaultResponseDTO("이메일을 발송했습니다.");
    }

    public String randomString() {
        StringBuilder sb = new StringBuilder();
        Random rnd = new Random();

        for(int i=0; i<8; i++) {
            switch (i%3) {
                case 0:
                    sb.append((char) ((rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    sb.append((char) ((rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    sb.append((rnd.nextInt(10)));
                    break;
            }
        }

        return sb.toString();
    }
}

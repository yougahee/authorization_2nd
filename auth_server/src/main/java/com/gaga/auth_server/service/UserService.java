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

    public DefaultResponseDTO insertUser(UserInfoRequestDTO userInfo) {
        User user = new User();

        Encryption encryption = new Encryption();
        String encryptPW = encryption.encode(userInfo.getPassword());

        log.info("encryptPW" + encryptPW);
        log.info("getSalt" + encryption.getSalt());

        user.setEmail(userInfo.getEmail());
        user.setName(userInfo.getName());
        user.setGender(userInfo.getGender());
        user.setPassword(encryptPW);
        user.setSalt(encryption.getSalt());

        userInfoRepository.save(user);

        return new DefaultResponseDTO("회원가입 성공!_!");
    }

    public LoginTokenResponseDTO getUserToken(UserLogInRequestDTO loginInfo) throws NullPointerException {

        Encryption encryption = new Encryption();

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

        //##이것도 이렇게 user를 마음대로 가져와도 되는건가...
        //##코드 중복 -> 쪼개야함
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

        String tempPW = randomString();
        mailDTO.setMessage(tempPW);

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
            switch(i%3) {
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

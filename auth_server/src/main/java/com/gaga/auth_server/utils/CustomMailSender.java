package com.gaga.auth_server.utils;

import com.gaga.auth_server.dto.MailDTO;
import com.gaga.auth_server.exception.MailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomMailSender {
    private final JavaMailSender javaMailSender;

    public void sendMail(MailDTO mailDTO) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("GAGA PLACE 임시비밀번호 전송");
            message.setFrom("ahrfus34@gmail.com");
            //원래는 mailDTO.getaddress()
            message.setTo("ahrfus34@gmail.com");
            message.setText("임시 비밀번호 : " + mailDTO.getMessage());

            javaMailSender.send(message);
        } catch (MailSendException e){
            throw new MailSendException();
        } catch (Exception e) {
            log.info(e.toString());
        }
    }
}

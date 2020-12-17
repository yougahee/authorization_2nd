package com.gaga.auth_server.service;

import com.gaga.auth_server.dto.MailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {
    private JavaMailSender mailSender;
    //꼭 유효해야하나?!?
    private static final String FROM_ADDRESS = "gagacompany@gmail.com";

    public void mailSend(MailDTO mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());

        mailSender.send(message);
    }
}

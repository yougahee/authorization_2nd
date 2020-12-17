package com.gaga.auth_server;

import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.exception.UnauthorizedException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;


//import com.gaga.auth_server.dto.response.ResponseEntity;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthControllerAdvice {
    private DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();

   /* @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        defaultResponseDTO.setMessage(e.getMessage());

        //## 궁금 -> 이렇게 써서 하는 게 과연 맞는 것인가?
        //에러처리를 어떻게 해야할지?!
        return new ResponseEntity(defaultResponseDTO, HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<String> UnauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}

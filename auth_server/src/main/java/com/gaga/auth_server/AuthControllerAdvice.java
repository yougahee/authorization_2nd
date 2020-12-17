package com.gaga.auth_server;

import com.gaga.auth_server.dto.response.DefaultResponseDTO;
import com.gaga.auth_server.exception.NoExistEmailException;
import com.gaga.auth_server.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class AuthControllerAdvice {
    private DefaultResponseDTO defaultResponseDTO = new DefaultResponseDTO();

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<DefaultResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        //Valid 마다 다르게 message를 제공할 수 있는지 찾아보기.
        defaultResponseDTO.setMessage("형식에 맞지 않습니다.");

        return ResponseEntity.ok().body(defaultResponseDTO);
    }

    @ExceptionHandler(value = {UnauthorizedException.class})
    public ResponseEntity<DefaultResponseDTO> unauthorizedException(UnauthorizedException e) {
        log.error(e.getMessage(), e);
        defaultResponseDTO.setMessage(e.errorMessage);
        return ResponseEntity.ok().body(defaultResponseDTO);
    }

    @ExceptionHandler(value = {NoExistEmailException.class})
    public ResponseEntity<DefaultResponseDTO> noExistEmailException(NoExistEmailException e) {
        defaultResponseDTO.setMessage(e.errorMessage);
        return ResponseEntity.ok().body(defaultResponseDTO);
    }
}

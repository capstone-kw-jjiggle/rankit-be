package gitbal.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<String> handleUserHasNoAuthenticated(JoinException e){
        return ResponseEntity.status(400).body("로그인 중 오류가 발생했습니다.");
    }





}

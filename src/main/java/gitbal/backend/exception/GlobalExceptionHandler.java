package gitbal.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<String> handleJoinException(JoinException e){
        return ResponseEntity.status(400).body("로그인 중 오류가 발생했습니다.");
    }

    @ExceptionHandler(UserRankException.class)
    public ResponseEntity<String> handleUserRankException(UserRankException e){
        return ResponseEntity.status(500).body("요청에 대한 처리를 제대로 수행하지 못했습니다.");
    }





}

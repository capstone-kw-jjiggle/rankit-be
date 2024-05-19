package gitbal.backend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(JoinException.class)
    public ResponseEntity<String> handleJoinException(JoinException e) {
        return ResponseEntity.status(400).body("로그인 중 오류가 발생했습니다.");
    }

    @ExceptionHandler(UserRankException.class)
    public ResponseEntity<String> handleUserRankException(UserRankException e) {
        return ResponseEntity.status(500).body("요청에 대한 처리를 제대로 수행하지 못했습니다.");
    }

    @ExceptionHandler(NotFoundUserException.class)
    public ResponseEntity<String> handleNotFoundUserException(NotFoundUserException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(WrongPageNumberException.class)
    public ResponseEntity<String> handleWrongPageNumberException(WrongPageNumberException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NotLoginedException.class)
    public ResponseEntity<String> handleNotLoginedException(WrongPageNumberException e){
        return ResponseEntity.status(401).body(e.getMessage());
    }


    @ExceptionHandler(MainPageFirstRankException.class)
    public ResponseEntity<String> handleMainPageFirstRankException(MainPageFirstRankException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundSchoolException.class)
    public ResponseEntity<String> handleNotFoundSchoolException(NotFoundSchoolException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundRegionException.class)
    public  ResponseEntity<String> handleNotFoundRegionException(NotFoundRegionException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(PageOutOfRangeException.class)
    public  ResponseEntity<String> handlePageOutOfRangeException(PageOutOfRangeException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }


    @ExceptionHandler(NotDrawUserException.class)
    public  ResponseEntity<String> handleDrawUserException(NotDrawUserException e){
        return ResponseEntity.status(500).body(e.getMessage());
    }

}

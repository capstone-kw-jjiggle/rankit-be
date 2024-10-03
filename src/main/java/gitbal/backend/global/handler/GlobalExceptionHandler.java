package gitbal.backend.global.handler;

import gitbal.backend.api.badge.dto.BadgeResponseDTO;
import gitbal.backend.api.badge.service.BadgeService;
import gitbal.backend.global.exception.BadgeException;
import gitbal.backend.global.exception.JoinException;
import gitbal.backend.global.exception.LogoutException;
import gitbal.backend.global.exception.NoTokenException;
import gitbal.backend.global.exception.NotDrawUserException;
import gitbal.backend.global.exception.NotFoundRefreshTokenException;
import gitbal.backend.global.exception.NotFoundRegionException;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.exception.NotUserPermissionException;
import gitbal.backend.global.exception.PageOutOfRangeException;
import gitbal.backend.global.exception.SchoolRankPageUserInfoBySchoolException;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.exception.UnivCertStartException;
import gitbal.backend.global.exception.UserHasNoRegionException;
import gitbal.backend.global.exception.UserHasNoSchoolException;
import gitbal.backend.global.exception.UserRankException;
import gitbal.backend.global.exception.UserRankingException;
import gitbal.backend.global.exception.WrongPageNumberException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final BadgeService badgeService;

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
    public ResponseEntity<String> handleNotLoginedException(NotLoginedException e){
        return ResponseEntity.status(401).body(e.getMessage());
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


    @ExceptionHandler(LogoutException.class)
    public  ResponseEntity<String> handleLogoutException(LogoutException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NotFoundRefreshTokenException.class)
    public  ResponseEntity<String> handleRefreshTokenException(NotFoundRefreshTokenException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }


    @ExceptionHandler(NoTokenException.class)
    public  ResponseEntity<String> handleNoTokenException(NoTokenException e){
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UnivCertStartException.class)
    public ResponseEntity<String> handleUnivCertStartException(UnivCertStartException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UnivCertCodeException.class)
    public ResponseEntity<String> handleUnivCertCodeException(UnivCertCodeException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(NotUserPermissionException.class)
    public ResponseEntity<String> handleNotUserPermissionException(NotUserPermissionException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(SchoolRankPageUserInfoBySchoolException.class)
    public ResponseEntity<String> handleSchoolRankPageUserInfoBySchoolException(SchoolRankPageUserInfoBySchoolException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserHasNoSchoolException.class)
    public ResponseEntity<String> handleNotFoundUserException(UserHasNoSchoolException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserHasNoRegionException.class)
    public ResponseEntity<String> handleNotFoundUserException(UserHasNoRegionException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(UserRankingException.class)
    public ResponseEntity<String> handleUserRankingException(UserRankingException e) {
        return ResponseEntity.status(400).body(e.getMessage());
    }

    @ExceptionHandler(BadgeException.class)
    public ResponseEntity<BadgeResponseDTO> BadgeException(BadgeException e) {
        return ResponseEntity.status(e.getStatusCode()).body(badgeService.getBadgeFailureResponse());
    }

}

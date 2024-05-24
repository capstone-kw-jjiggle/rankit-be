package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class LogoutException extends RuntimeException{

    public LogoutException() {
        super("로그아웃에 실패하였습니다.");
    }
}

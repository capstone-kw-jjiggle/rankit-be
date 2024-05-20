package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotDrawUserException extends RuntimeException {

    public NotDrawUserException() {
        super("사용자 회원 탈퇴에 실패했습니다.");
    }
}

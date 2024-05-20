package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotLoginedException extends RuntimeException{

    public NotLoginedException() {
        super("인증된 유저가 없는 상태로 요청하셨습니다. github 로그인을 진행해주세요.");
    }
}

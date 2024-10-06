package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserHasNoMajorLanguageException extends RuntimeException {
    public UserHasNoMajorLanguageException() {
        super("사용자의 주 언어가 없습니다. 아직 업데이트가 되어있지 않을 수 있습니다");
    }
}

package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserHasNoSchoolException extends RuntimeException{
    public UserHasNoSchoolException() {
        super("해당 유저는 학교에 등록되어 있지 않습니다.");
    }
}

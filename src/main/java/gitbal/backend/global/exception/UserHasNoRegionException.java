package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserHasNoRegionException extends RuntimeException {
    public UserHasNoRegionException() {
        super("해당 유저는 지역에 등록되어 있지 않습니다.");
    }
}

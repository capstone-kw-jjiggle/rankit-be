package gitbal.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException() {
        super("해당 이름의 유저가 존재하지 않습니다");
    }
}

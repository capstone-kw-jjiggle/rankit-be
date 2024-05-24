package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class NoTokenException extends RuntimeException{

    public NoTokenException() {
        super("토큰이 존재하지 않습니다.");
    }
}

package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundRefreshTokenException extends RuntimeException{

    public NotFoundRefreshTokenException() {
        super("refereshToken을 찾을 수 없습니다.");
    }
}

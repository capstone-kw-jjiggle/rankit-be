package gitbal.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class WrongPageNumberException extends RuntimeException{

    public WrongPageNumberException(int page) {
        super("올바른 page 값을 던져주시기 바랍니다 현재 페이지는: " + page + "입니다.");
    }
}

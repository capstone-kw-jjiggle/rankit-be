package gitbal.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundSchoolException extends RuntimeException{
    public NotFoundSchoolException() {
        super("school를 찾던 도중 오류가 발생하였습니다.");
    }

}

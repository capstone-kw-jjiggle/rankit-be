package gitbal.backend.global.exception;

public class WrongUnivDomainException extends RuntimeException {
    public WrongUnivDomainException() {
        super("올바르지 않은 대학교 도메인입니다.");
    }

    public WrongUnivDomainException(String message) {
        super(message);
    }
}

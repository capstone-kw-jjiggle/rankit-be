package gitbal.backend.global.exception;

public class UnivCertProcessException extends RuntimeException{

    public UnivCertProcessException() {
        super("인증코드 검증 과정에서 오류가 발생했습니다.(10분이 경과되었거나 이미 인증이 완료되었습니다.)");
    }
}

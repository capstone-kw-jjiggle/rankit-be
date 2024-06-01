package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class UnivCertStartException extends RuntimeException {
  public UnivCertStartException() {super("인증번호 발송에 실패했습니다.");}
}

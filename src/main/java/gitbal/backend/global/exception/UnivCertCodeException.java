package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class UnivCertCodeException extends RuntimeException{
  public UnivCertCodeException() {super("번호 인증에 실패했습니다.");}
}

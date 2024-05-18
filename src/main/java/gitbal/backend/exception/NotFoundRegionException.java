package gitbal.backend.exception;

import lombok.experimental.StandardException;

@StandardException
public class NotFoundRegionException extends RuntimeException{
  public NotFoundRegionException() {
    super("해당 이름의 지역이 존재하지 않습니다.");
  }
}

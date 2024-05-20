package gitbal.backend.global.exception;

import lombok.experimental.StandardException;
import org.springframework.data.domain.PageRequest;

@StandardException
public class PageOutOfRangeException extends RuntimeException{
  public PageOutOfRangeException() {super("유효한 페이지 범위를 입력해주세요.");}

}

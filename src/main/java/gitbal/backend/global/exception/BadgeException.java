package gitbal.backend.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class BadgeException extends RuntimeException {
  private final HttpStatus statusCode;
}

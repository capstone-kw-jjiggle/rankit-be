package gitbal.backend.global.exception;

public class NotUserPermissionException extends RuntimeException{
  public NotUserPermissionException() {
    super("권한이 없습니다.");
  }
}

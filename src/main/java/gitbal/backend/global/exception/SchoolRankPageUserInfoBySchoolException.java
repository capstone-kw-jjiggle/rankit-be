package gitbal.backend.global.exception;

import java.util.Objects;
import lombok.experimental.StandardException;

@StandardException
public class SchoolRankPageUserInfoBySchoolException extends
    RuntimeException {
    public SchoolRankPageUserInfoBySchoolException(String msg) {
        super(msg);
    }


}

package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class UserRankingException extends RuntimeException{
    public UserRankingException() {
        super("올바르지 않은 유저 랭킹입니다. 업데이트가 진행되지 않았을 수 있습니다!");
    }
}

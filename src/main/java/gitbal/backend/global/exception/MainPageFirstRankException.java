package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class MainPageFirstRankException extends RuntimeException{
    public MainPageFirstRankException() {
        super("firstRank 찾던 도중 오류가 발생하였습니다.");
    }
}

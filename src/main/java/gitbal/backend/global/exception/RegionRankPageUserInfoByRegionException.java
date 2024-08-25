package gitbal.backend.global.exception;

import lombok.experimental.StandardException;

@StandardException
public class RegionRankPageUserInfoByRegionException extends
    RuntimeException {
    public RegionRankPageUserInfoByRegionException(String msg) {
        super(msg);
    }


}

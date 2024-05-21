package gitbal.backend.global.util;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SurroundingRankStatus {

    private int forwardCount;
    private int backwardCount;

    public static SurroundingRankStatus calculateUserForwardBackward(int forwardCount, int backwardCount,
        int range) {
        if (forwardCount == 0 || forwardCount == 1) {
            return new SurroundingRankStatus(forwardCount, range * 2 - forwardCount);
        } else if (backwardCount == 0 || backwardCount == 1) {
            return new SurroundingRankStatus(range * 2 - backwardCount, backwardCount);
        } else {
            forwardCount = range;
            backwardCount = range;
            return new SurroundingRankStatus(forwardCount, backwardCount);
        }
    }


    public static SurroundingRankStatus calculateSchoolRegionForwardBackward(int forwardCount, int backwardCount,
        int range) {
        if (forwardCount == 0 || forwardCount == 1 || forwardCount == 2) {
            return new SurroundingRankStatus(forwardCount, range * 2 - forwardCount);
        } else if (backwardCount == 0 || backwardCount == 1 ||backwardCount == 2) {
            return new SurroundingRankStatus(range * 2 - backwardCount, backwardCount);
        } else {
            forwardCount = range;
            backwardCount = range;
            return new SurroundingRankStatus(forwardCount, backwardCount);
        }
    }


}

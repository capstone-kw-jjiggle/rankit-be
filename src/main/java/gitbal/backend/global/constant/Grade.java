package gitbal.backend.global.constant;

import lombok.Getter;

@Getter
public enum Grade {
    YELLOW(0,20000),
    GREEN(20001, 40000),
    BLUE(40001, 60000),
    RED(60001, 80000),
    GREY(80001, 100000),
    PURPLE(100001, Long.MAX_VALUE);

    Grade(long underBound, long upperBound) {
        this.underBound = underBound;
        this.upperBound = upperBound;
    }

    private final long underBound;
    private final long upperBound;

    public static Grade nextGrade(Grade grade) {
        switch (grade) {
            case YELLOW -> {
                return Grade.GREEN;
            }
            case GREEN -> {
                return Grade.BLUE;
            }
            case BLUE -> {
                return Grade.RED;
            }
            case RED -> {
                return Grade.GREY;
            }
            case GREY, PURPLE -> {
                return Grade.PURPLE;
            }
            default -> throw new IllegalArgumentException("알 수 없는 등급입니다: " + grade);
        }
    }


}

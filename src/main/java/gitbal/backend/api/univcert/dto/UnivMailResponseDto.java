package gitbal.backend.api.univcert.dto;

public record UnivMailResponseDto(Boolean isSuccess, String msg) {

    public static UnivMailResponseDto of(Boolean isSuccess, String msg) {
        return new UnivMailResponseDto(isSuccess, msg);
    }
}

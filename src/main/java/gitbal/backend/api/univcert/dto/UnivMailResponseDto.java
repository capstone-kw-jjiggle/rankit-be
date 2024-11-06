package gitbal.backend.api.univcert.dto;

public record UnivMailResponseDto(String msg) {

    public static UnivMailResponseDto of(String msg) {
        return new UnivMailResponseDto(msg);
    }
}

package gitbal.backend.api.guestBookPage.dto;



public record GuestBookResponseDto(Long userId, String username, String profileImg, String boardContent) { }

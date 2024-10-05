package gitbal.backend.domain.guestbook.application;

import gitbal.backend.api.guestBookPage.dto.GuestBookResponseDto;
import gitbal.backend.api.guestBookPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.domain.user.User;
import java.util.List;

public interface GuestBookService {

    List<GuestBookResponseDto> getGuestBooks();

    void saveGuestBook(User user, String content);

}

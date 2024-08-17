package gitbal.backend.domain.guestbook.application;

import gitbal.backend.api.dashboardPage.dto.GuestBookResponseDto;
import gitbal.backend.api.dashboardPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.domain.user.User;
import java.util.List;

public interface GuestBookService {

    List<GuestBookResponseDto> getGuestBooks();

    void saveGuestBook(User user, GuestBookWriteRequestDto dto);

}

package gitbal.backend.domain.guestbook.application;

import gitbal.backend.api.dashboardPage.dto.GuestBookResponseDto;
import gitbal.backend.api.dashboardPage.dto.GuestBookWriteRequestDto;
import java.util.List;

public interface GuestBookService {

    List<GuestBookResponseDto> getGuestBooks();

    void saveGuestBook(GuestBookWriteRequestDto dto);

}

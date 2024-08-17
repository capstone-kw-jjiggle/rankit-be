package gitbal.backend.api.dashboardPage.facade;

import gitbal.backend.api.dashboardPage.dto.GuestBookResponseDto;
import gitbal.backend.api.dashboardPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.domain.guestbook.application.GuestBookServiceImpl;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.global.exception.NotFoundUserException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestBookPageFacade{

    private final GuestBookServiceImpl guestBookService;
    private final UserRepository userService;


    @Transactional
    public List<GuestBookResponseDto> getDashBoardDtos() {
        return guestBookService.getGuestBooks();
    }

    @Transactional
    public void saveDashBoard(GuestBookWriteRequestDto dto) {
        User user = userService.findById(dto.userId())
            .orElseThrow(() -> new NotFoundUserException("게시글 등록 중 사용자를 찾지 못하여 실패하였습니다."));
        guestBookService.saveGuestBook(user, dto);
    }
}

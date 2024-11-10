package gitbal.backend.api.guestBookPage.facade;

import gitbal.backend.api.guestBookPage.dto.GuestBookResponseDto;
import gitbal.backend.api.guestBookPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.domain.guestbook.application.GuestBookServiceImpl;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.exception.NotFoundUserException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestBookPageFacade{

    private final GuestBookServiceImpl guestBookService;
    private final UserRepository userRepository;


    @Transactional
    public List<GuestBookResponseDto> getDashBoardDtos() {
        return guestBookService.getGuestBooks();
    }

    @Transactional
    public void saveDashBoard(Authentication authentication, String content) {
        User user = userRepository.findByNickname(authentication.getName()).orElseThrow(NotFoundUserException::new);
        guestBookService.saveGuestBook(user, content);
    }
}

package gitbal.backend.domain.guestbook.application;


import gitbal.backend.api.guestBookPage.dto.GuestBookResponseDto;
import gitbal.backend.api.guestBookPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.domain.guestbook.GuestBook;
import gitbal.backend.domain.guestbook.infra.repository.GuestBookRepository;
import gitbal.backend.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestBookServiceImpl implements GuestBookService {

    private final GuestBookRepository guestBookRepository;

    @Override
    public List<GuestBookResponseDto> getGuestBooks() {
        return guestBookRepository.findGuestBookTop30().stream()
            .map(g ->
                new GuestBookResponseDto(g.getUser().getId(), g.getUser().getProfile_img(),
                    g.getBoardContent())
            ).toList();
    }

    @Override
    public void saveGuestBook(User user, GuestBookWriteRequestDto dto) {
        GuestBook guestBook = GuestBook.builder()
            .user(user)
            .boardContent(dto.content())
            .build();
        guestBookRepository.save(guestBook);
    }

}

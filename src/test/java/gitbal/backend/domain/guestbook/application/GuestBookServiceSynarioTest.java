package gitbal.backend.domain.guestbook.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gitbal.backend.api.dashboardPage.dto.GuestBookResponseDto;
import gitbal.backend.api.dashboardPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.domain.guestbook.GuestBook;
import gitbal.backend.domain.guestbook.infra.repository.GuestBookRepository;
import gitbal.backend.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class GuestBookServiceSynarioTest {

    @Mock
    GuestBookRepository guestBookRepository;

    @InjectMocks
    GuestBookServiceImpl guestBookService;

    @Test
    void getSynarioGuestBooks() {

        // given
        User mockUser = mock(User.class);
        when(mockUser.getId()).thenReturn(1L);
        when(mockUser.getProfile_img()).thenReturn("profile_img1");

        GuestBook mockGuestBook = mock(GuestBook.class);
        when(mockGuestBook.getUser()).thenReturn(mockUser);
        when(mockGuestBook.getBoardContent()).thenReturn("Content1");

        when(guestBookRepository.findGuestBookTop30()).thenReturn(List.of(mockGuestBook, mockGuestBook, mockGuestBook));

        // when
        List<GuestBookResponseDto> result = guestBookService.getGuestBooks();

        // assertions
        assertNotNull(result);
        assertEquals(3, result.size());


        // verify
        verify(guestBookRepository, times(1)).findGuestBookTop30();
    }

    @Test
    void saveGuestBook() {
        //given
        User mockUser = mock(User.class);
        GuestBookWriteRequestDto mockDto = mock(GuestBookWriteRequestDto.class);

        GuestBook build = GuestBook.builder()
            .user(mockUser)
            .boardContent("content")
            .build();
        //when
        when(guestBookRepository.save(any(GuestBook.class))).thenReturn(build);

        //then
        guestBookService.saveGuestBook(mockUser, mockDto);

        //verify
        verify(guestBookRepository, times(1)).save(any(GuestBook.class));

    }
}
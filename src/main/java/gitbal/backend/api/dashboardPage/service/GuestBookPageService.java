package gitbal.backend.api.dashboardPage.service;

import gitbal.backend.api.dashboardPage.dto.GuestBookResponseDto;
import gitbal.backend.api.dashboardPage.dto.GuestBookWriteRequestDto;
import java.util.List;

public interface GuestBookPageService {

    List<GuestBookResponseDto> getDashBoardDtos();

    void saveDashBoard(GuestBookWriteRequestDto dto);
}

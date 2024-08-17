package gitbal.backend.api.dashboardPage.controller;

import gitbal.backend.api.dashboardPage.dto.GuestBookResponseDto;
import gitbal.backend.api.dashboardPage.dto.GuestBookWriteRequestDto;
import gitbal.backend.api.dashboardPage.facade.GuestBookPageFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/guestbook")
@RequiredArgsConstructor
@Tag(name = "게시판 페이지를 위한 API")
public class GuestBookPageController {

    private final GuestBookPageFacade guestBookPageFacade;


    @GetMapping
    @Operation(summary = "현재 등록되어져있는 방명록들 최신 순으로 30개 표시 ", description = "방명록 목록을 보여주는 api입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "게시판 목록 전달 성공"),
        @ApiResponse(responseCode = "500", description = "게시판 목록을 서버 측에서 오류가 발생하여 전달하지 못했습니다.")
    })
    public ResponseEntity<List<GuestBookResponseDto>> getGuestBooks() {
        return ResponseEntity.ok(guestBookPageFacade.getDashBoardDtos());
    }


    @PostMapping("/write")
    @Operation(summary = "유저가 방명록을 작성할 때", description = "방명록을 작성하는 api 입니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "방명록 목록 전달 성공"),
        @ApiResponse(responseCode = "500", description = "방명록 목록을 서버 측에서 오류가 발생하여 전달하지 못했습니다.")
    })
    public ResponseEntity<String> writeGuestBook(@RequestBody GuestBookWriteRequestDto dto) {
        guestBookPageFacade.saveDashBoard(dto);
        return ResponseEntity.ok("방명록 등록에 성공하였습니다.");
    }



}

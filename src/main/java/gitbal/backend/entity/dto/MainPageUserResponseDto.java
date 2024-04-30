package gitbal.backend.entity.dto;

import gitbal.backend.domain.PageInfoDto;
import java.util.List;
import lombok.Getter;


@Getter
public class MainPageUserResponseDto {

    private List<MainPageUserDto> userList;
    private int currentPage;
    private int startPage;
    private int endPage;
    private int totalPages;

    public MainPageUserResponseDto(List<MainPageUserDto> userList, PageInfoDto pageInfoDto) {
        this.userList = userList;
        this.currentPage = pageInfoDto.currentPage();
        this.startPage = pageInfoDto.startPage();
        this.endPage = pageInfoDto.endPage();
        this.totalPages = pageInfoDto.totalPage();
    }

    public static MainPageUserResponseDto of(List<MainPageUserDto> userList, PageInfoDto pageInfoDto){
        return new MainPageUserResponseDto(userList, pageInfoDto);
    }

}

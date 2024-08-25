package gitbal.backend.api.regionPage.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
public class UserPageListByRegionResponseDto {
    private List<UserInfoByRegion> userInfoByRegion;
    private int currentPage;
    private int start;
    private int end;
    private int totalPages;


    @Builder(builderMethodName = "withAll")
    public UserPageListByRegionResponseDto(List<UserInfoByRegion> userInfoByRegion, int page, Long total) {
        this.userInfoByRegion = userInfoByRegion;
        this.currentPage = page;
        int totalCount = total.intValue();

        if(emptyPage(page)){
            this.totalPages = 0;
            this.start = 0;
            this.end = 0;
            return;
        }

        int pageSize = 10;
        this.totalPages = (int) Math.ceil((double) totalCount / pageSize);

        this.end = (int) (Math.ceil(page / 10.0)) * 10;
        this.start = end - 9;

        int last = (int) (Math.ceil(totalCount / (double) pageSize));
        end = end > last ? last : end;

        boolean IsOverSize = page > totalPages;

        if (IsOverSize) {
            start = 0;
            end = 0;
        }
    }

    private static boolean emptyPage(int page) {
        return page == 0;
    }
}

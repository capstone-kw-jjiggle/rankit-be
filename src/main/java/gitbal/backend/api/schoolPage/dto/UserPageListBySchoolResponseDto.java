package gitbal.backend.api.schoolPage.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
public class UserPageListBySchoolResponseDto {

  private List<UserInfoBySchool> userInfoBySchools;
  private int currentPage;
  private int start;
  private int end;
  private int totalPages;


  @Builder(builderMethodName = "withAll")
  public UserPageListBySchoolResponseDto(List<UserInfoBySchool> userInfoBySchools, int page, Long total) {
    this.userInfoBySchools = userInfoBySchools;
    this.currentPage = page;
    int totalCount = total.intValue();

    if(emptyPage(page)){
        this.totalPages = 0;
        this.start = 0;
        this.end = 0;
        return;
    }

    int pageSize = 14;
    this.totalPages = (int) Math.ceil((double) totalCount / pageSize);

    this.end = (int) (Math.ceil(page / 14.0)) * 14;
    this.start = end - 13;

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

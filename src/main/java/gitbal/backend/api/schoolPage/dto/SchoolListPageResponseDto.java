package gitbal.backend.api.schoolPage.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
public class SchoolListPageResponseDto<E> {

  private List<E> schoolList;
  private int currentPage;
  private int start;
  private int end;
  private int totalPages;


  @Builder(builderMethodName = "withALl")
  public SchoolListPageResponseDto(List<E> schoolList, int page, Long total) {
    this.schoolList = schoolList;
    this.currentPage = page;
    int totalCount = total.intValue();

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
}

package gitbal.backend.domain.regionPage.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;


@Data
public class RegionListPageResponseDto<E> {

  private List<E> regionList;


  @Builder(builderMethodName = "withALl")
  public RegionListPageResponseDto(List<E> regionList) {
    this.regionList = regionList;
  }
}

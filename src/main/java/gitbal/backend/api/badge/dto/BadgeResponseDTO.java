package gitbal.backend.api.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class BadgeResponseDTO {
  private String userRank;
  private String score;
  private String langName;
  private String grade;
}

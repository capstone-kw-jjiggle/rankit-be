package gitbal.backend.api.badge.dto;

import gitbal.backend.global.constant.Grade;
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
  private Grade grade;
}

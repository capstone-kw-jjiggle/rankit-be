package gitbal.backend.api.badge.service;

import gitbal.backend.api.badge.dto.BadgeResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface BadgeService {

  public BadgeResponseDTO getBadgeResponse(String username);
  public BadgeResponseDTO getBadgeFailureResponse();
}

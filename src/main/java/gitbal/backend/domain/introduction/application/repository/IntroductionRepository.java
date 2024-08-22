package gitbal.backend.domain.introduction.application.repository;

import gitbal.backend.api.userPage.dto.IntroductionupdateRequestDto;
import gitbal.backend.domain.introduction.Introduction;


public interface IntroductionRepository {
  Introduction createIntroductionAndReturn();

  void updateIntroduction(Introduction introduction, IntroductionupdateRequestDto dto);
}

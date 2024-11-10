package gitbal.backend.domain.introduction.infra;

import gitbal.backend.api.userPage.dto.IntroductionupdateRequestDto;
import gitbal.backend.domain.introduction.Introduction;
import gitbal.backend.domain.introduction.application.repository.IntroductionRepository;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class IntroductionRepositoryImpl implements IntroductionRepository {


  private final IntroductionJpaRepository introductionJpaRepository;

  @Override
  public Introduction createIntroductionAndReturn() {
    Introduction introduction = new Introduction();
    introductionJpaRepository.save(introduction);
    return introduction;
  }

  @Override
  public void updateIntroduction(Introduction introduction, IntroductionupdateRequestDto dto) {
    Introduction newIntroduction = Introduction.of(
                                                   introduction.getId(),
                                                   dto.getTitle(),
                                                   dto.getOneLiner(),
                                                   dto.getGoodAt(),
                                                   dto.getLearningGoal());
    introductionJpaRepository.save(newIntroduction);
  }
}
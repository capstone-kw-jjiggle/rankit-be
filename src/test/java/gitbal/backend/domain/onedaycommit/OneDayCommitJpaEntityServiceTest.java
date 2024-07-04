package gitbal.backend.domain.onedaycommit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import gitbal.backend.domain.onedaycommit.application.OneDayCommit;
import gitbal.backend.domain.onedaycommit.application.repository.OneDayCommitRepository;
import gitbal.backend.domain.onedaycommit.infra.OneDayCommitJpaEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OneDayCommitJpaEntityServiceTest {

    @Mock
    private OneDayCommitRepository oneDayCommitRepository;

    @InjectMocks
    private OneDayCommitService oneDayCommitService;



    @Test
    @DisplayName("어제 커밋이 있을 때")
    void calculateRecentCommit() {
        // given
        boolean hasYesterdayCommit = true;
        OneDayCommitJpaEntity testCommit = OneDayCommitJpaEntity.of(hasYesterdayCommit);

        // when
        when(oneDayCommitRepository.save(any(OneDayCommit.class))).thenReturn(testCommit);
        OneDayCommitJpaEntity oneDayCommitJpaEntity = oneDayCommitService.calculateRecentCommit(hasYesterdayCommit);

        // then
        Assertions.assertThat(oneDayCommitJpaEntity.getSteadyCount()).isGreaterThan(0L);

    }

    @Test
    @DisplayName("어제 커밋이 없을 때")
    void recentCommitNone() {
        // given
        boolean hasYesterdayCommit = false;
        OneDayCommitJpaEntity testCommit = OneDayCommitJpaEntity.of(hasYesterdayCommit);

        // when
        when(oneDayCommitRepository.save(any(OneDayCommit.class))).thenReturn(testCommit);
        OneDayCommitJpaEntity oneDayCommitJpaEntity = oneDayCommitService.calculateRecentCommit(hasYesterdayCommit);

        // then
        assertEquals(0L, oneDayCommitJpaEntity.getSteadyCount());
    }


    @Test
    @DisplayName("어제 커밋이 있을때 업데이트 하는 경우")
    void updateHasRecentCommit() {

        OneDayCommitJpaEntity oneDayCommitJpaEntity = OneDayCommitJpaEntity.of(true);
        // given
        boolean hasYesterdayCommit = true;

        // when
        oneDayCommitService.updateCommit(oneDayCommitJpaEntity, hasYesterdayCommit);

        // then
        Assertions.assertThat(oneDayCommitJpaEntity.getSteadyCount()).isGreaterThan(0L);
    }

    @Test
    @DisplayName("어제 커밋이 있을때 업데이트 하는 경우")
    void updateNoRecentCommit() {

        OneDayCommitJpaEntity oneDayCommitJpaEntity = OneDayCommitJpaEntity.of(true);
        // given
        boolean hasYesterdayCommit = false;

        // when
        oneDayCommitService.updateCommit(oneDayCommitJpaEntity, hasYesterdayCommit);

        // then
        Assertions.assertThat(oneDayCommitJpaEntity.getSteadyCount()).isEqualTo(0L);
    }

}
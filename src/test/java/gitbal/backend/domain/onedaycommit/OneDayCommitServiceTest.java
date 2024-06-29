package gitbal.backend.domain.onedaycommit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OneDayCommitServiceTest {

    @Mock
    private OneDayCommitRepository oneDayCommitRepository;

    @InjectMocks
    private OneDayCommitService oneDayCommitService;



    @Test
    @DisplayName("어제 커밋이 있을 때")
    void calculateRecentCommit() {
        // given
        boolean hasYesterdayCommit = true;
        OneDayCommit testCommit = OneDayCommit.of(hasYesterdayCommit);

        // when
        when(oneDayCommitRepository.save(any(OneDayCommit.class))).thenReturn(testCommit);
        OneDayCommit oneDayCommit = oneDayCommitService.calculateRecentCommit(hasYesterdayCommit);

        // then
        Assertions.assertThat(oneDayCommit.getSteadyCount()).isGreaterThan(0L);

    }

    @Test
    @DisplayName("어제 커밋이 없을 때")
    void recentCommitNone() {
        // given
        boolean hasYesterdayCommit = false;
        OneDayCommit testCommit = OneDayCommit.of(hasYesterdayCommit);

        // when
        when(oneDayCommitRepository.save(any(OneDayCommit.class))).thenReturn(testCommit);
        OneDayCommit oneDayCommit = oneDayCommitService.calculateRecentCommit(hasYesterdayCommit);

        // then
        assertEquals(0L, oneDayCommit.getSteadyCount());
    }


    @Test
    @DisplayName("어제 커밋이 있을때 업데이트 하는 경우")
    void updateHasRecentCommit() {

        OneDayCommit oneDayCommit = OneDayCommit.of(true);
        // given
        boolean hasYesterdayCommit = true;

        // when
        oneDayCommitService.updateCommit(oneDayCommit, hasYesterdayCommit);

        // then
        Assertions.assertThat(oneDayCommit.getSteadyCount()).isGreaterThan(0L);
    }

    @Test
    @DisplayName("어제 커밋이 있을때 업데이트 하는 경우")
    void updateNoRecentCommit() {

        OneDayCommit oneDayCommit = OneDayCommit.of(true);
        // given
        boolean hasYesterdayCommit = false;

        // when
        oneDayCommitService.updateCommit(oneDayCommit, hasYesterdayCommit);

        // then
        Assertions.assertThat(oneDayCommit.getSteadyCount()).isEqualTo(0L);
    }

}
package gitbal.backend.domain.region;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gitbal.backend.domain.region.application.RegionRaceStatus;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.region.application.repository.RegionRepository;
import gitbal.backend.domain.user.User;
import gitbal.backend.global.util.SurroundingRankStatus;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RegionServiceTest {


    private final int REGION_AROUND_RANGE = 3;

    @Mock
    private RegionRepository regionRepository;



    @Spy
    private Region region;

    @InjectMocks
    private RegionService regionService;




    @Test
    @DisplayName("지역 찾기 로직")
    void findByRegionName(){
        // given
        String regionName = "test";
        Region testRegion = mock(Region.class);

        // when
        when(regionRepository.findByRegionName(regionName)).thenReturn(
            Optional.of(testRegion));
        Region result = regionService.findByRegionName(regionName);

        // then
        Assertions.assertThat(testRegion).isEqualTo(result);
    }

    @Test
    @DisplayName("새 유저 가입 로직 - topContributor가 변경되는 경우")
    void joinNewUserScoreUpdateContributor(){
        //given
        User user = mock(User.class);
        when(user.getScore()).thenReturn(100L);
        when(user.getRegion()).thenReturn(region);
        when(user.getNickname()).thenReturn("test");

        region.setScore(0L);
        region.setContributorScore(0L);
        region.setTopContributor("originalUser");

        //when
        regionService.joinNewUserScore(user);

        //then
        verify(region, times(1)).addScore(100L);
        verify(region, times(1)).updateContributerInfo("test", 100L);
        Assertions.assertThat(region.getContributorScore()).isEqualTo(100L);
        Assertions.assertThat(region.getScore()).isEqualTo(100L);
        Assertions.assertThat(region.getTopContributor()).isEqualTo("test");
    }

    @Test
    @DisplayName("새 유저 가입 로직 -  topContributor가 변경되지 않는 경우(최고 기여자와 점수가 같거나 작은 경우)")
    void joinNewUserScoreNotUpdateContributor(){
        //given
        User user = mock(User.class);
        User user2 = mock(User.class);

        when(user2.getScore()).thenReturn(200L);
        when(user2.getRegion()).thenReturn(region);
        when(user2.getNickname()).thenReturn("test2");
        when(user.getScore()).thenReturn(100L);
        when(user.getRegion()).thenReturn(region);
        when(user.getNickname()).thenReturn("test");

        region.setScore(200L);
        region.setContributorScore(200L);
        region.setTopContributor("originalUser");

        //when
        regionService.joinNewUserScore(user);
        regionService.joinNewUserScore(user2);

        //then
        verify(region, times(1)).addScore(100L);
        verify(region, times(1)).addScore(200L);
        verify(region, times(1)).updateContributerInfo("test", 100L);
        verify(region, times(1)).updateContributerInfo("test2", 200L);
        Assertions.assertThat(region.getContributorScore()).isEqualTo(200L);
        Assertions.assertThat(region.getScore()).isEqualTo(500L);
        Assertions.assertThat(region.getTopContributor()).isEqualTo("originalUser");
    }


    @Test
    @DisplayName("지역 점수 경쟁 상태 조회 로직")
    void findRegionScoreRaced(){
        Long score = 100L;
        int forwardCount = 10;
        int backwardCount = 5;
        when(regionRepository.regionScoreRacedForward(score)).thenReturn(10);
        when(regionRepository.regionScoreRacedBackward(score)).thenReturn(5);

        SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateSchoolRegionForwardBackward(
            forwardCount, backwardCount, REGION_AROUND_RANGE);

        List<Region> mockRegion = mock(List.class);
        when(regionRepository.regionScoreRaced(score, surroundingRankStatus.getForwardCount(),
            surroundingRankStatus.getBackwardCount())).thenReturn(mockRegion);

        RegionRaceStatus regionRaceStatus = RegionRaceStatus.of(mockRegion);

        RegionRaceStatus regionScoreRaced = regionService.findRegionScoreRaced(score);

        Assertions.assertThat(regionScoreRaced.getAroundUsers()).isEqualTo(regionRaceStatus.getAroundUsers());
        verify(regionRepository, times(1)).regionScoreRacedForward(score);
        verify(regionRepository, times(1)).regionScoreRacedBackward(score);
        verify(regionRepository, times(1)).regionScoreRaced(score, surroundingRankStatus.getForwardCount(),
            surroundingRankStatus.getBackwardCount());
    }



}
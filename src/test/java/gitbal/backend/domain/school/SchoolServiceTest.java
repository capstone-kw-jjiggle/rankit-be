package gitbal.backend.domain.school;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gitbal.backend.domain.user.User;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.global.util.SurroundingRankStatus;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.data.domain.Sort;

@ExtendWith(MockitoExtension.class)
class SchoolServiceTest {


    private final int SCHOOL_AROUND_RANGE = 3;

    @Mock
    private SchoolRepository schoolRepository;

    @Spy
    private School school;

    @InjectMocks
    private SchoolService schoolService;

    @Test
    @DisplayName("학교 찾기 로직")
    void findBySchoolName(){
        // given
        String schoolName = "test";
        School testSchool = mock(School.class);

        // when
        when(schoolRepository.findBySchoolName(schoolName)).thenReturn(
            Optional.of(testSchool));
        School result = schoolService.findBySchoolName(schoolName);

        // then
        Assertions.assertThat(testSchool).isEqualTo(result);
    }


    @Test
    @DisplayName("새 유저 가입 로직 - topContributor가 변경되는 경우")
    void joinNewUserScoreUpdateContributor(){
        //given
        User user = mock(User.class);
        when(user.getScore()).thenReturn(100L);
        when(user.getSchool()).thenReturn(school);
        when(user.getNickname()).thenReturn("test");

        school.setScore(0L);
        school.setContributorScore(0L);
        school.setTopContributor("originalUser");
        //when
        schoolService.joinNewUserScore(user);

        //then
        verify(school, times(1)).addScore(100L);
        verify(school, times(1)).updateContributerInfo("test", 100L);
        Assertions.assertThat(school.getContributorScore()).isEqualTo(100L);
        Assertions.assertThat(school.getScore()).isEqualTo(100L);
        Assertions.assertThat(school.getTopContributor()).isEqualTo("test");
    }



    @Test
    @DisplayName("새 유저 가입 로직 -  topContributor가 변경되지 않는 경우(최고 기여자와 점수가 같거나 작은 경우)")
    void joinNewUserScoreNotUpdateContributor(){
        //given
        User user = mock(User.class);
        User user2 = mock(User.class);

        when(user2.getScore()).thenReturn(200L);
        when(user2.getSchool()).thenReturn(school);
        when(user2.getNickname()).thenReturn("test2");
        when(user.getScore()).thenReturn(100L);
        when(user.getSchool()).thenReturn(school);
        when(user.getNickname()).thenReturn("test");


        school.setScore(200L);
        school.setContributorScore(200L);
        school.setTopContributor("originalUser");

        //when
        schoolService.joinNewUserScore(user);
        schoolService.joinNewUserScore(user2);

        //then
        verify(school, times(1)).addScore(100L);
        verify(school, times(1)).addScore(200L);
        verify(school, times(1)).updateContributerInfo("test", 100L);
        verify(school, times(1)).updateContributerInfo("test2", 200L);
        Assertions.assertThat(school.getContributorScore()).isEqualTo(200L);
        Assertions.assertThat(school.getScore()).isEqualTo(500L);
        Assertions.assertThat(school.getTopContributor()).isEqualTo("originalUser");
    }

    @Test
    @DisplayName("학교 랭킹 업데이트 로직")
    void updateSchoolRank(){
        //given
        List<School> schools = new ArrayList<>(
            Arrays.asList(
                School.of("test1", 0L, "testUser", 10L),
                School.of("test2", 10L, "testUser", 20L),
                School.of("test3", 20L, "testUser", 30L)
            )
        );
        schools.sort((o1, o2) -> (int) (o2.getScore() - o1.getScore()));
        when(schoolRepository.findAll(Sort.by("score").descending())).thenReturn(schools);

        //when
        schoolService.updateSchoolRank();

        //then
        verify(schoolRepository, times(1)).findAll(Sort.by("score").descending());
        Assertions.assertThat(schools.get(0).getSchoolRank()).isEqualTo(1);
        Assertions.assertThat(schools.get(1).getSchoolRank()).isEqualTo(2);
        Assertions.assertThat(schools.get(2).getSchoolRank()).isEqualTo(3);
    }

    @Test
    @DisplayName("학교 등급 업데이트 로직")
    void updateSchoolGrade(){
        //given
        List<School> schools = new ArrayList<>(
            Arrays.asList(
                School.of("test1", 0L, "testUser", 10L),
                School.of("test2", 10L, "testUser", 20L),
                School.of("test3", 20L, "testUser", 30L)
            )
        );

        schools.sort((o1, o2) -> (int) (o2.getScore() - o1.getScore()));
        when(schoolRepository.findAll(Sort.by("score").descending())).thenReturn(schools);

        //when
        schoolService.updateSchoolGrade();

        //then
        verify(schoolRepository, times(1)).findAll(Sort.by("score").descending());
        Assertions.assertThat(schools.get(0).getGrade().toString()).isEqualTo(Grade.GREY.toString());
        Assertions.assertThat(schools.get(1).getGrade().toString()).isEqualTo(Grade.RED.toString());
        Assertions.assertThat(schools.get(2).getGrade().toString()).isEqualTo(Grade.BLUE.toString());

    }






}
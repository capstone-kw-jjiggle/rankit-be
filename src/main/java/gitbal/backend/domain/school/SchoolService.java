package gitbal.backend.domain.school;

import gitbal.backend.api.schoolPage.service.SchoolRankService;
import gitbal.backend.domain.user.User;
import gitbal.backend.global.exception.NotFoundSchoolException;
import gitbal.backend.global.util.JoinUpdater;
import gitbal.backend.global.util.ScheduleUpdater;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolService {


    private final int FIRST_RANK = 1;
    private final ScheduleUpdater<School> schoolScheduleUpdater;
    private final SchoolRepository schoolRepository;
    private final SchoolRankService schoolRankService;


    public School findBySchoolName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName)
            .orElseThrow(NotFoundSchoolException::new);
    }

    public void joinNewUserScore(User findUser) {
        JoinUpdater schoolJoinUpdater = new SchoolJoinUpdater();
        schoolJoinUpdater.process(findUser);
    }


    public void updateByUserScore(School school, String username, Long oldScore, Long newScore) {
        schoolScheduleUpdater.update(school, username, oldScore, newScore);
    }


    public void updateSchoolRank() {
        List<School> schools = schoolRepository.findAll(Sort.by("score").descending());
        updateRank(schools);
    }

    public List<School> getAllSchoolList() {
        List<School> schools = schoolRepository.findAll();
        return schools;
    }

    private void updateRank(List<School> schools) {
        int rank = FIRST_RANK;
        int prevRank = FIRST_RANK;

        for (int i = 0; i < schools.size(); i++) {
            School school = schools.get(i);
            if (i > 0 && !Objects.equals(schools.get(i - 1).getScore(), school.getScore())) {
                prevRank = rank;
            }
            school.setSchoolRank(prevRank);
            rank = prevRank + 1;
        }
        schoolRepository.saveAll(schools);
    }

    public void updateScore(School prevSchool, School school, Long score) {
        school.addScore(score);
        prevSchool.minusScore(score);
        updateSchoolRank();
    }


    public void updateScore(School school, Long score) {
        school.addScore(score);
        updateSchoolRank();
    }
}

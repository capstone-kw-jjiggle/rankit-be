package gitbal.backend.service;

import gitbal.backend.domain.SchoolRaceStatus;
import gitbal.backend.domain.SurroundingRankStatus;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SchoolService {


    private final int SCHOOL_AROUND_RANGE = 3;
    private final SchoolRepository schoolRepository;

    public School findBySchoolName(String schoolName) {
        return schoolRepository.findBySchoolName(schoolName)
            .orElseThrow(() -> new JoinException("존재하지 않는 학교 이름입니다."));
    }

    public void joinNewUserScore(User findUser) {
        Long score = findUser.getScore();
        School school = findUser.getSchool();
        school.addScore(score);
    }


    public SchoolRaceStatus findSchoolScoreRaced(Long score) {
        int forwardCount = schoolRepository.schoolScoreRacedForward(score);
        int backwardCount = schoolRepository.schoolScoreRacedBackward(score);
        log.info("forwardCount = {} backwardCount = {}", forwardCount, backwardCount);

        SurroundingRankStatus surroundingRankStatus = SurroundingRankStatus.calculateSchoolRegionForwardBackward(
            forwardCount, backwardCount, SCHOOL_AROUND_RANGE);

        log.info("after forwardCount = {} backwardCount = {}", forwardCount, backwardCount);
        return SchoolRaceStatus.of(
            schoolRepository.schoolScoreRaced(score, surroundingRankStatus.forwardCount(),
                surroundingRankStatus.backwardCount()));
    }
}

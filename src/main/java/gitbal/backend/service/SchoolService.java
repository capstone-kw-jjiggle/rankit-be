package gitbal.backend.service;

import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.exception.JoinException;
import gitbal.backend.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolService {

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
}

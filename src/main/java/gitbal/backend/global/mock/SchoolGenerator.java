package gitbal.backend.global.mock;

import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SchoolGenerator {

    private final SchoolRepository schoolRepository;
    private static long id=1;

    private static int count=0;

    public School generateSchool() {
        School byId = schoolRepository.findById(id).get();
        if(count<5) {
            count++;
        }else{
            count=0;
            id++;
        }
        return byId;
    }
}

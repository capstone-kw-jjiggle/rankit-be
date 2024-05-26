package gitbal.backend.api.info.service;

import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SchoolInfoService implements InfoService {

    private SchoolRepository schoolRepository;

    @Override
    public List<String> findAllList() {
        return schoolRepository.findAll().stream()
            .map(School::getSchoolName)
            .toList();
    }
}

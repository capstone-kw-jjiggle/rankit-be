package gitbal.backend.domain.majorlanguage.application;

import org.springframework.http.ResponseEntity;

public interface TopLanguageService {

    ResponseEntity<String> requestTopLanguageQuery(String username);

}

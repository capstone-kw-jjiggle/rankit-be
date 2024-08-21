package gitbal.backend.domain.majorlanguage.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import gitbal.backend.domain.majorlanguage.MajorLanguage;
import gitbal.backend.domain.majorlanguage.MajorLanguageDto;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
class MajorLanguageServiceTest {

    @Mock
    private MajorLanguageRepository majorLanguageRepository;

    @Mock
    private TopLanguageService topLanguageService;

    @InjectMocks
    private MajorLanguageService majorLanguageService;

    private String getTestJsonResponse() {
        return """
            {
                "data": {
                    "user": {
                        "repositories": {
                            "nodes": [
                                {
                                    "languages": {
                                        "edges": [
                                            {
                                                "node": {"name": "Java"},
                                                "size": 100
                                            },
                                            {
                                                "node": {"name": "Python"},
                                                "size": 50
                                            },
                                            {
                                                "node": {"name": "JavaScript"},
                                                "size": 30
                                            },
                                            {
                                                "node": {"name": "C"},
                                                "size": 20
                                            },
                                            {
                                                "node": {"name": "C++"},
                                                "size": 10
                                            }
                                        ]
                                    }
                                }
                            ]
                        }
                    }
                }
            }
            """;
    }
}
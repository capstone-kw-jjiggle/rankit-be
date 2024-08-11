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

    @Test
    @DisplayName("유저의 상위 언어를 조회한다.")
    void getUserTopLaunguages() {
        // given
        String username = "testUser";
        String jsonResponse = getTestJsonResponse();

        // when
        when(topLanguageService.requestTopLanguageQuery(anyString())).thenReturn(
            ResponseEntity.ok(jsonResponse));
        List<MajorLanguageJpaEntity> result = majorLanguageService.getUserTopLaunguages(
            username);

        // then
        assertEquals(5, result.size());
        verify(topLanguageService, times(1)).requestTopLanguageQuery(username);

    }



    @Test
    @DisplayName("유저의 언어를 업데이트한다. 두 리스트가 동일한 경우")
    void updateUserLanguageSameSize() {
        // given
        List<MajorLanguage> oldLanguages = List.of(
            MajorLanguage.builder().id(1L).majorLanguage("Java").languageCount(100L).build(),
            MajorLanguage.builder().id(2L).majorLanguage("Python").languageCount(200L).build()
            );

        List<MajorLanguage> updatedLanguages = List.of(
            MajorLanguage.builder().id(1L).majorLanguage("Java").languageCount(100L).build(),
            MajorLanguage.builder().id(2L).majorLanguage("Python").languageCount(200L).build()
        );

        // when
        majorLanguageService.updateUserLanguage(oldLanguages, updatedLanguages);

        // then
         verify(majorLanguageRepository, never()).save(any(MajorLanguageJpaEntity.class));
         verify(majorLanguageRepository, never()).deleteById(any(Long.class));
         verify(majorLanguageRepository, never()).save(any(MajorLanguageJpaEntity.class));

    }

    @Test
    @DisplayName("유저의 언어를 업데이트한다. 오래된 리스트가 더 큰  경우")
    void updateWhenUpdatedSizeLessThanOldSize(){
        //given
        List<MajorLanguage> oldLanguages = new ArrayList<>(List.of(
            MajorLanguage.builder().id(1L).majorLanguage("Java").languageCount(100L).build(),
            MajorLanguage.builder().id(2L).majorLanguage("Python").languageCount(200L).build(),
            MajorLanguage.builder().id(3L).majorLanguage("JavaScript").languageCount(200L).build()
        ));

        List<MajorLanguage> updatedLanguages = new ArrayList<>(List.of(
            MajorLanguage.builder().id(1L).majorLanguage("Java").languageCount(100L).build(),
            MajorLanguage.builder().id(2L).majorLanguage("Python").languageCount(200L).build()
        ));

        //when
        majorLanguageService.updateUserLanguage(oldLanguages, updatedLanguages);


        //then
        verify(majorLanguageRepository, times(2)).updateMajorLanguage(any(Long.class), any(
            MajorLanguageDto.class));
        verify(majorLanguageRepository, times(1)).deleteById(3L);
    }





    @Test
    @DisplayName("유저의 언어를 업데이트한다. 오래된 리스트가 작은 경우")
    void updateWhenUpdatedSizeGreaterThanOldSize(){
        //given
        List<MajorLanguage> oldLanguages = new ArrayList<>(List.of(
            MajorLanguage.builder().id(1L).majorLanguage("Java").languageCount(100L).build(),
            MajorLanguage.builder().id(2L).majorLanguage("Python").languageCount(200L).build()
        ));

        List<MajorLanguage> updatedLanguages = new ArrayList<>(List.of(
            MajorLanguage.builder().id(1L).majorLanguage("Java").languageCount(100L).build(),
            MajorLanguage.builder().id(2L).majorLanguage("Python").languageCount(200L).build(),
            MajorLanguage.builder().id(3L).majorLanguage("JavaScript").languageCount(200L).build()
        ));

        //when
        majorLanguageService.updateUserLanguage(oldLanguages, updatedLanguages);


        //then
        verify(majorLanguageRepository, times(1)).save(any(MajorLanguageJpaEntity.class));
        verify(majorLanguageRepository, never()).deleteById(any(Long.class));
        verify(majorLanguageRepository, never()).updateMajorLanguage(any(Long.class), any(
            MajorLanguageDto.class));
    }


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
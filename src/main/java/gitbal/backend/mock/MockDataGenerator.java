package gitbal.backend.mock;

import gitbal.backend.entity.MajorLanguage;
import gitbal.backend.entity.OneDayCommit;
import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.repository.MajorLanguageRepository;
import gitbal.backend.repository.OneDayCommitRepository;
import gitbal.backend.repository.RegionRepository;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import gitbal.backend.service.RegionService;
import gitbal.backend.service.SchoolService;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;



@Component
@RequiredArgsConstructor
@Slf4j
public class MockDataGenerator implements CommandLineRunner {

    // Repositories and services declarations are omitted for brevity
    private final SchoolRepository schoolRepository;
    private final RegionRepository regionRepository;
    private final MajorLanguageRepository majorLanguageRepository;
    private final OneDayCommitRepository oneDayCommitRepository;
    private final UserRepository userRepository;
    private final SchoolService schoolService;
    private final RegionService regionService;

    private final Random random = new Random();

    // Constants for the total number of schools and regions
    private static final int TOTAL_SCHOOLS = 30;
    private static final int TOTAL_REGIONS = 10;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Start runner to create mock data");

        for (int i = 0; i < 100; i++) {
            // Fetch random school and region
            School randomSchool = schoolRepository.findById((long) (random.nextInt(TOTAL_SCHOOLS) + 1)).orElse(null);
            Region randomRegion = regionRepository.findById((long) (random.nextInt(TOTAL_REGIONS) + 1)).orElse(null);

            // Create a random user
            User newUser = createUser(randomSchool, randomRegion);

            // Create a list of MajorLanguage entities for this user
            List<MajorLanguage> majorLanguages = createRandomMajorLanguagesForUser(newUser);
            majorLanguages.forEach(majorLanguageRepository::save);

            // Create and save OneDayCommit entity
            OneDayCommit oneDayCommit = OneDayCommit.of(random.nextBoolean());
            oneDayCommitRepository.save(oneDayCommit);

            // Update user with the new relations
            newUser.joinUpdateUser(randomSchool, randomRegion, oneDayCommit, majorLanguages, newUser.getNickname(), newUser.getScore(), newUser.getProfile_img());
            User saveUser = userRepository.save(newUser);
            scoring(saveUser);



        }

        log.info("Mock data creation completed");
    }

    private User createUser(School school, Region region) {
        String randomNickname = "User" + random.nextInt(1000); // Example nickname
        String randomProfileImg = "https://example.com/image" + random.nextInt(100); // Example profile image URL
        Long randomScore = (long) random.nextInt(1000,100000); // Example userScore

        return User.builder()
            .school(school)
            .region(region)
            .nickname(randomNickname)
            .score(randomScore) // Random userScore
            .profile_img(randomProfileImg)
            .build();
    }

    private List<MajorLanguage> createRandomMajorLanguagesForUser(User user) {
        List<MajorLanguage> majorLanguages = new ArrayList<>();
        // Generate a random number of languages for each user
        int languagesCount = 5; // Random number of languages between 1 and 5

        for (int i = 0; i < languagesCount; i++) {
            String[] languages = {"Java", "C#", "JavaScript", "Python"};
            String randomLanguage = languages[random.nextInt(languages.length)];
            Long randomLanguageCount = (long) random.nextInt(100); // Example count

            MajorLanguage majorLanguage = MajorLanguage.builder()
                .majorLanguage(randomLanguage)
                .languageCount(randomLanguageCount)
                .build();
            majorLanguages.add(majorLanguage);
        }

        return majorLanguages;
    }

    private void scoring(User findUser) {
        schoolService.joinNewUserScore(findUser);
        regionService.joinNewUserScore(findUser);
    }
}
package gitbal.backend.global.mock;


import gitbal.backend.api.guestBookPage.facade.GuestBookPageFacade;
import gitbal.backend.domain.guestbook.application.GuestBookService;
import gitbal.backend.domain.introduction.application.repository.IntroductionRepository;
import gitbal.backend.domain.region.application.repository.RegionRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.school.SchoolRepository;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.SchoolService;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    private final UserRepository userRepository;
    private final UserService userService;
    private final SchoolService schoolService;
    private final RegionService regionService;
    private final GuestBookService guestBookService;
    private final IntroductionRepository introductionRepository;


    private final Random random = new Random();
    private final GenerateRealMockUser generateRealMockUser = new GenerateRealMockUser();
    private SchoolGenerator schoolGenerator;
    private RegionGenerator regionGenerator;
    private final ImgGenerator imgGenerator = new ImgGenerator();

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        schoolGenerator = new SchoolGenerator(schoolRepository);
        regionGenerator = new RegionGenerator(regionRepository);
        log.info("Start runner to create mock data");
        List<String> names = new HashSet<>(generateRealMockUser.getNames()).stream().toList();
        log.info("name size was {}" , names.size());

        for (int i = 0; i < names.size(); i++) {
            String username = names.get(i);
            log.info("now username {}", username);
            log.info("now i {}", i);
            if (userRepository.findByNickname(username).isPresent()) {
                log.info("duplicate");
                continue;
            }
            School school = schoolGenerator.generateSchool();
            Region region = regionGenerator.generateRegion();
            String imgUrl = imgGenerator.imgGenerator(i);
            Long randomScore = (long) random.nextInt(0, 120000); // Example userScore

            User user = User.builder()
                    .school(school)
                    .region(region)
                    .nickname(username)
                    .score(randomScore)
                    .profile_img(imgUrl)
                    .grade(Grade.PURPLE)
                    .build();

            createIntroductionForUser(user);
            String randomMajorLanguagesForUser = createRandomMajorLanguagesForUser(user);
            scoring(user);

            // Update user with the new relations
            user.joinMockUpdateUser(school, region, randomMajorLanguagesForUser,
                    user.getNickname(), user.getScore(), user.getProfile_img(), 0, user.getIntroduction());

            userRepository.save(user);
            guestBookService.saveGuestBook(user, "안녕하세요!");

            if (i % 50 == 0 && i != 0) {
                userRepository.flush();
                log.info("Flushed and cleared at count {}", i);
            }
        }

        // Test를 위한 나(이승준)의 githubid와 동일한 nickname data

        String lee = "leesj000603";
        if (userRepository.findByNickname(lee).isPresent()) {
            log.info("duplicate");
            return;
        }

        createUserWithNickname(lee);
        createUserWithNickname("jamooooong");
        userService.updateUserRank(); //user 순위 업데이트
        userService.updateUserGrade(); // user 등급 업데이트
        schoolService.updateSchoolRank(); // school 순위 업데이트
        log.info("Mock data creation completed!!!!!!");
    }


    private void createUserWithNickname(String nickName) {
        School school = schoolRepository.findById(1L)
                .orElse(null);
        Region region = regionRepository.findById(1L)
                .orElse(null);

        User user = User.builder()
                .school(school)
                .region(region)
                .nickname(nickName)
                .score(140000L)
                .profile_img("https://i.namu.wiki/i/tyx9GSyT6U1vpeboPZpUimd2wgkQsB7SDBIe8nFnHRlgCrXRpp6_C9QRvz61A9KRyf_oP1rUHT8Ykwc3CQF9nDQ4aFR5_5ZueLRbnodvtMpF_wPCbRMis09h_JwvVMIRv12bnrAXy6ecLT959C9a4w.webp")
                .grade(Grade.PURPLE)
                .build();

        String randomMajorLanguagesForUser = createRandomMajorLanguagesForUser(user);

        user.joinMockUpdateUser(school, region, randomMajorLanguagesForUser,
                user.getNickname(), user.getScore(), user.getProfile_img(), 0, introductionRepository.createIntroductionAndReturn());
        userRepository.save(user);
    }

    private String createRandomMajorLanguagesForUser(User user) {
        // Generate a random number of languages for each user
        int languagesCount = 1; // Random number of languages between 1 and 5
        int i = random.nextInt(5);
        String[] languages = {"Java", "C#", "JavaScript", "Python", "C"};
        String randomLanguage = languages[i];

        return randomLanguage;
    }

    private User createIntroductionForUser(User user) {
        user.setIntroduction(introductionRepository.createIntroductionAndReturn());
        return user;
    }


    private void scoring(User findUser) {
        schoolService.joinNewUserScore(findUser);
        regionService.joinNewUserScore(findUser);
    }


}
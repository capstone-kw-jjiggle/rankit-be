package gitbal.backend.global.mock;


import gitbal.backend.domain.region.application.repository.RegionRepository;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.constant.Grade;
import gitbal.backend.domain.majorlanguage.infra.MajorLanguageJpaEntity;
import gitbal.backend.domain.onedaycommit.infra.OneDayCommitJpaEntity;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.majorlanguage.application.repository.MajorLanguageRepository;
import gitbal.backend.domain.onedaycommit.infra.OneDayCommitJpaRepository;
import gitbal.backend.domain.school.SchoolRepository;
import gitbal.backend.domain.user.UserRepository;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.SchoolService;
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
  private final OneDayCommitJpaRepository oneDayCommitRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final SchoolService schoolService;
  private final RegionService regionService;

  private final Random random = new Random();

  // Constants for the total number of schools and regions
  private static final int TOTAL_SCHOOLS = 408;
  private static final int TOTAL_REGIONS = 10;

  @Override
  @Transactional
  public void run(String... args) throws Exception {
    log.info("Start runner to create mock data");

    for (int i = 0; i < 1000; i++) {
      String randomNickname = "User" + i; // Example nickname

      if(userRepository.findByNickname(randomNickname).isPresent()) {
        log.info("continue");
        continue;
      }
      // Fetch random school and region
      School randomSchool = schoolRepository.findById((long) (random.nextInt(TOTAL_SCHOOLS) + 1))
          .orElse(null);
      Region randomRegion = regionRepository.findById((long) (random.nextInt(TOTAL_REGIONS) + 1))
          .orElse(null);

      // Create a random user
      User newUser = createUser(randomNickname, randomSchool, randomRegion, i);

      // Create a list of MajorLanguage entities for this user
      MajorLanguageJpaEntity majorLanguage = createRandomMajorLanguagesForUser(newUser);
      majorLanguageRepository.save(majorLanguage);

      // Create and save OneDayCommit entity
      OneDayCommitJpaEntity oneDayCommitJpaEntity = OneDayCommitJpaEntity.of(random.nextBoolean());
      oneDayCommitRepository.save(oneDayCommitJpaEntity);

      // Update user with the new relations
      newUser.joinUpdateUser(randomSchool, randomRegion, oneDayCommitJpaEntity, majorLanguage,
          newUser.getNickname(), newUser.getScore(), newUser.getProfile_img(), 0);
      User saveUser = userRepository.save(newUser);
      scoring(saveUser);
    }
    // Test를 위한 나(이승준)의 githubid와 동일한 nickname data

    String lee = "leesj000603";
    if(userRepository.findByNickname(lee).isPresent()){
      log.info("duplicate");
      return;
    }

    createUserWithNickname(lee);
    userService.updateUserRank(); //user 순위 업데이트
    userService.updateUserGrade(); // user 등급 업데이트
    insertRegionSchoolTopContributorInfo();
    schoolService.updateSchoolRank(); // school 순위 업데이트
    schoolService.updateSchoolGrade(); // school 등급 업데이트
    log.info("Mock data creation completed!!!!!!");
  }


  private User createUser(String randomNickname, School school, Region region, int index) {

    String randomProfileImg =
        "https://example.com/image" + random.nextInt(100); // Example profile image URL
    Long randomScore = (long) random.nextInt(1000, 100000); // Example userScore

    return User.builder()
        .school(school)
        .region(region)
        .nickname(randomNickname)
        .score(randomScore) // Random userScore
        .profile_img(randomProfileImg)
        .grade(Grade.YELLOW)
        .build();
  }

  private void createUserWithNickname(String nickName) {
    School school = schoolRepository.findById(1L)
        .orElse(null);
    Region region = regionRepository.findById(1L)
        .orElse(null);
    OneDayCommitJpaEntity oneDayCommitJpaEntity = OneDayCommitJpaEntity.of(true);
    oneDayCommitRepository.save(oneDayCommitJpaEntity);

    User leesj000603 = User.builder()
        .school(school)
        .region(region)
        .nickname(nickName)
        .score(500L)
        .profile_img("sdqsdqwefqwef")
        .grade(Grade.PURPLE)
        .build();



    MajorLanguageJpaEntity majorLanguage = createRandomMajorLanguagesForUser(leesj000603);
    majorLanguageRepository.save(majorLanguage);

    OneDayCommitJpaEntity oneDayCommitJpaEntity1 = OneDayCommitJpaEntity.of(true);
    oneDayCommitRepository.save(oneDayCommitJpaEntity1);
    leesj000603.joinUpdateUser(school, region, oneDayCommitJpaEntity, majorLanguage,
        leesj000603.getNickname(), leesj000603.getScore(), leesj000603.getProfile_img(), 0);
    userRepository.save(leesj000603);

  }

  private MajorLanguageJpaEntity createRandomMajorLanguagesForUser(User user) {
    // Generate a random number of languages for each user
    int languagesCount = 1; // Random number of languages between 1 and 5
    int i = random.nextInt(5);
    String[] languages = {"Java", "C#", "JavaScript", "Python", "C"};
    String randomLanguage = languages[i];
    Long randomLanguageCount = (long) random.nextInt(100); // Example count

    return MajorLanguageJpaEntity.builder()
        .majorLanguage(randomLanguage)
        .languageCount(randomLanguageCount)
        .user(user)
        .build();
  }


  private void scoring(User findUser) {
    schoolService.joinNewUserScore(findUser);
    regionService.joinNewUserScore(findUser);
  }


  private void insertRegionSchoolTopContributorInfo() {
    regionService.insertTopContributorInfo();
    schoolService.insertSchoolTopContributorInfo();
  }


}
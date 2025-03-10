package gitbal.backend.api.userPage.service;

import gitbal.backend.api.univcert.service.MailService;
import gitbal.backend.api.userPage.RandomFriendPicker;
import gitbal.backend.api.userPage.dto.FriendSuggestDto;
import gitbal.backend.api.userPage.dto.IntroductionResponseDto;
import gitbal.backend.api.userPage.dto.IntroductionupdateRequestDto;
import gitbal.backend.api.userPage.dto.ModifySchoolRequestDto;
import gitbal.backend.api.userPage.dto.UserPageUserInfoResponseDto;
import gitbal.backend.domain.introduction.Introduction;
import gitbal.backend.domain.introduction.application.repository.IntroductionRepository;
import gitbal.backend.domain.region.Region;
import gitbal.backend.domain.region.application.RegionService;
import gitbal.backend.domain.school.School;
import gitbal.backend.domain.school.SchoolService;
import gitbal.backend.domain.user.User;
import gitbal.backend.domain.user.UserService;
import gitbal.backend.global.exception.NotLoginedException;
import gitbal.backend.global.exception.UnivCertCodeException;
import gitbal.backend.global.security.CustomUserDetails;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPageService {
  private final UserService userService;
  private final SchoolService schoolService;
  private final RegionService regionService;
  private final MailService mailService;
  private final RandomFriendPicker randomUserPicker;
  private final IntroductionRepository introductionRepository;

  //지역이나 학교 수정해도 수정하기 전 후의 지역점수나 학교점수에 유저 점수 직접 더하고 빼지 않음. (어짜피 점수 업데이트 할 때 반영되니깐)
  @Transactional
  public void modifySchoolName(Authentication authentication, ModifySchoolRequestDto modifySchoolRequestDto) {
    User user = checkAuthAndGetUser(authentication);
    if(!mailService.certCode(modifySchoolRequestDto.email(), modifySchoolRequestDto.certificateCode()))
        throw new UnivCertCodeException();
    updateUserSchool(user, modifySchoolRequestDto.modifySchoolName());
  }

  @Transactional
  public void modifyRegionName(Authentication authentication, String newRegionName){
    User user = checkAuthAndGetUser(authentication);
    updateUserRegion(user, newRegionName);
  }

  public ArrayList<FriendSuggestDto> getFriendSuggestionList(Authentication authentication){

    if (authentication == null) {
      return randomUserPicker.getAllRandomFriendList()
          .stream()
          .map(this::convertToFriendSuggestDTO)
          .collect(Collectors.toCollection(ArrayList::new));
    } else {
      User user = checkAuthAndGetUser(authentication);
      ArrayList<FriendSuggestDto> friendSuggestionList =
          Objects.requireNonNull(getResultFriends(user))
              .stream()
              .map(this::convertToFriendSuggestDTO)
              .collect(Collectors.toCollection(ArrayList::new));
      Collections.shuffle(friendSuggestionList);
      return friendSuggestionList;
    }
  }

  public IntroductionResponseDto getIntroduction(String userName){
    User user = userService.findByUserName(userName);
    return converToIntroductionDTO(user.getIntroduction());
  }

  public void updateIntroduction(Authentication authentication, IntroductionupdateRequestDto dto){
    User loginUser = checkAuthAndGetUser(authentication);
    Introduction introduction = loginUser.getIntroduction();
    introductionRepository.updateIntroduction(introduction, dto);
  }

  private List<User> getResultFriends(User user){
    return randomUserPicker.getFriendList(user);
  }

  private IntroductionResponseDto converToIntroductionDTO(Introduction introduction){
    return IntroductionResponseDto.of(
        introduction.getTitle(),
        introduction.getOneLiner(),
        introduction.getGoodAt(),
        introduction.getLearningGoal()
    );
  }

  private FriendSuggestDto convertToFriendSuggestDTO(User user) {
    School school = user.getSchool();
    Region region = user.getRegion();
    if(Objects.isNull(school) && Objects.isNull(region)){
      return getRegionSchoolEmptySuggestedDto(user);
    }
    else if(Objects.isNull(user.getSchool()))
        return getOnlySchoolEmptySuggestedDto(user);
    else if(Objects.isNull(user.getRegion()))
      return getOnlyRegionEmptySuggestedDto(user);

    return getNotEmptySuggestedDto(user);
  }

  private FriendSuggestDto getNotEmptySuggestedDto(User user) {
    return FriendSuggestDto.of(
            user.getNickname(),
            user.getGrade(),
            user.getMajorLanguage(),
            user.getSchool().getSchoolName(),
            user.getRegion().getRegionName(),
            user.getProfile_img()
    );
  }

  private FriendSuggestDto getOnlyRegionEmptySuggestedDto(User user) {
    return FriendSuggestDto.of(
            user.getNickname(),
            user.getGrade(),
            user.getMajorLanguage(),
            user.getSchool().getSchoolName(),
            null,
            user.getProfile_img()
    );
  }

  private FriendSuggestDto getOnlySchoolEmptySuggestedDto(User user) {
    return FriendSuggestDto.of(
            user.getNickname(),
            user.getGrade(),
            user.getMajorLanguage(),
            null,
            user.getRegion().getRegionName(),
            user.getProfile_img()
    );
  }

  private FriendSuggestDto getRegionSchoolEmptySuggestedDto(User user) {
    return FriendSuggestDto.of(
            user.getNickname(),
            user.getGrade(),
            user.getMajorLanguage(),
            null,
            null,
            user.getProfile_img()
    );
  }

  private User checkAuthAndGetUser(Authentication authentication) {
    if (authentication == null) {
      throw new NotLoginedException();
    }
    CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
    String userName = principal.getNickname();
    return userService.findByUserName(userName);
  }

  private void updateUserSchool(User user, String schoolName) {
    userService.updateUserSchool(user, schoolService.findBySchoolName(schoolName));
  }

  private void updateUserRegion(User user, String regionName) {
    userService.updateUserRegion(user, regionService.findByRegionName(regionName));
  }

  @Transactional(readOnly = true)
  public UserPageUserInfoResponseDto makeUserInfoResponse(String username) {
      return userService.makeUserInfoResponse(username);
  }
}

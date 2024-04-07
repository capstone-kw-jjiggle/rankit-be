package gitbal.backend.service;

import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserInfoDto;
import gitbal.backend.repository.RegionRepository;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserInfoService {

  private final UserRepository userRepository;
  private final RegionRepository regionRepository;
  private final SchoolRepository schoolRepository;

  public UserInfoDto getUserInfoByUserName(String userName) { // Todo: 현재 컨트롤러로 보내는 과정에서 문제 있음.
    System.out.println("서비스 진입");
    Optional<User> result = userRepository.findByNickname(userName);

    if (result.isPresent()) {
      User user = result.get();
      Long univId = user.getSchool().getId();
      Long regionId = user.getRegion().getRegionId();
      String imgName = user.getProfile_img().toString();
      String userTitle = null;

      Optional<Region> userRegion = regionRepository.findById(regionId);
      Optional<School> userUniv = schoolRepository.findById(univId);

      String regionName = userRegion.get().getRegionName();
      String univName = userUniv.get().getSchoolName();

      UserInfoDto userInfoDto = new UserInfoDto(userName, univName, regionName, imgName,
          userTitle);
      return userInfoDto;
    } else {
      // 값이 존재하지 않을 때 404 상태 코드를 반환하는 예외를 던집니다.
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }
  }

}

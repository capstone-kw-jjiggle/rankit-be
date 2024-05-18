package gitbal.backend.service;

import gitbal.backend.entity.Region;
import gitbal.backend.entity.School;
import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserInfoDto;
import gitbal.backend.exception.NotFoundRegionException;
import gitbal.backend.exception.NotFoundSchoolException;
import gitbal.backend.exception.NotFoundUserException;
import gitbal.backend.repository.RegionRepository;
import gitbal.backend.repository.SchoolRepository;
import gitbal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.HibernateException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoService {

  private final UserRepository userRepository;
  private final RegionRepository regionRepository;
  private final SchoolRepository schoolRepository;

  public ResponseEntity<UserInfoDto> getUserInfoByUserName(String userName) {

    User user = userRepository.findByNickname(userName).orElseThrow(NotFoundUserException::new);

    Long univId = user.getSchool().getId();
    Long regionId = user.getRegion().getRegionId();
    String imgName = user.getProfile_img().toString();
    String userTitle = null;

    Region userRegion = regionRepository.findById(regionId).orElseThrow(NotFoundRegionException::new);
    School userUniv = schoolRepository.findById(univId).orElseThrow(NotFoundSchoolException::new);

    String regionName = userRegion.getRegionName();
    String univName = userUniv.getSchoolName();

    UserInfoDto userInfoDto = new UserInfoDto(userName, univName, regionName, imgName, userTitle);

    return ResponseEntity.ok(userInfoDto);

  }

}

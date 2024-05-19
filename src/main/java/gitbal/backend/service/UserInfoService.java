package gitbal.backend.service;

import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserInfoDto;
import gitbal.backend.exception.NotFoundUserException;
import gitbal.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserInfoService {

    private final UserRepository userRepository;


    @Transactional(readOnly = true)
    public ResponseEntity<UserInfoDto> getUserInfoByUserName(String userName) {
        User user = userRepository.findByNickname(userName).
            orElseThrow(NotFoundUserException::new);
        UserInfoDto userInfoDto = UserInfoDto.of(user);
        return ResponseEntity.ok(userInfoDto);
    }

}

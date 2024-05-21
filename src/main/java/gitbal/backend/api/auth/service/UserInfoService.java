package gitbal.backend.api.auth.service;


import gitbal.backend.api.auth.dto.UserInfoDto;
import gitbal.backend.domain.user.User;
import gitbal.backend.global.exception.NotFoundUserException;
import gitbal.backend.domain.user.UserRepository;
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

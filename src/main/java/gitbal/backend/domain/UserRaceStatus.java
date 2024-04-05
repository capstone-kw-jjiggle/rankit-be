package gitbal.backend.domain;

import gitbal.backend.entity.User;
import gitbal.backend.entity.dto.UserRankRaceResponseDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record UserRaceStatus(List<User> aroundUsers) {
    public static UserRaceStatus of(List<User> aroundUsers) {
        return new UserRaceStatus(aroundUsers);
    }

    public void sortAroundUsers(){
        aroundUsers.sort(Comparator.comparing(User::getScore));
    }

    public void addUser(User user){
        aroundUsers.add(user);
    }


    public List<UserRankRaceResponseDto> toUserRankRaceResponseDto(List<User> users) {
        List<UserRankRaceResponseDto> responseDtos = new ArrayList<>();
        for (int i=0; i<users.size(); i++) {
            String profileImg = users.get(i).getProfile_img();
            String nickname = users.get(i).getNickname();
            responseDtos.add(UserRankRaceResponseDto.of(nickname, profileImg));
        }
        return responseDtos;
    }
}

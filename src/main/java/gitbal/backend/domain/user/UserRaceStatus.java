package gitbal.backend.domain.user;


import gitbal.backend.global.util.RaceStatus;
import gitbal.backend.api.userPage.dto.UserRankRaceResponseDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserRaceStatus implements RaceStatus<User> {


    private List<User> aroundUsers;

    public static UserRaceStatus of(List<User> aroundUsers) {
        return new UserRaceStatus(aroundUsers);
    }

    @Override
    public void sortAroundEntitys() {
        aroundUsers.sort(Comparator.comparing(User::getScore));
    }

    @Override
    public void addEntity(User user) {
        aroundUsers.add(user);
    }

    public List<UserRankRaceResponseDto> toResponseDto(List<User> around) {
        List<UserRankRaceResponseDto> responseDtos = new ArrayList<>();
        for (int i=0; i<around.size(); i++) {
            String profileImg = around.get(i).getProfile_img();
            String nickname = around.get(i).getNickname();
            responseDtos.add(UserRankRaceResponseDto.of(nickname, profileImg));
        }
        return responseDtos;
    }




}

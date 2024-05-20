package gitbal.backend.global.util;


import gitbal.backend.global.entity.User;
import gitbal.backend.domain.userPage.dto.UserRankRaceResponseDto;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record UserRaceStatus(List<User> aroundUsers) implements RaceStatus<User> {
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

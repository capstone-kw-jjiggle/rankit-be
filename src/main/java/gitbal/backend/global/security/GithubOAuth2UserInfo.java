package gitbal.backend.global.security;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GithubOAuth2UserInfo {

    private Map<String, Object> attributes;


    public GithubOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    public static GithubOAuth2UserInfo of(Map<String, Object> attributes) {
        return new GithubOAuth2UserInfo(attributes);
    }

    // TODO 이후 추가되는 컬럼들에 따라서 여기에서 조절해서 진행

    public String getNickname(){
        return attributes.get("login").toString();
    }

    public String getAvatarImgUrl(){
        return attributes.get("avatar_url").toString();
    }

}

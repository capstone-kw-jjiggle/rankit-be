package gitbal.backend.global.util;

import gitbal.backend.domain.user.User;

public interface JoinUpdater {

    void process(User user);

}

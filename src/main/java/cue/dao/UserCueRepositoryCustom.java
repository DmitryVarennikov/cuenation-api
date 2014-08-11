package cue.dao;

import user.domain.User;

import java.util.List;

public interface UserCueRepositoryCustom {

    void markCuesAsViewed(User user, List<String> ids);

}

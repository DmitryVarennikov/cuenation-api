package cuenation.api.cue.persistence;

import cuenation.api.user.domain.User;

import java.util.List;

public interface UserCueRepositoryCustom {

    void markCuesAsViewed(User user, List<String> ids);

}

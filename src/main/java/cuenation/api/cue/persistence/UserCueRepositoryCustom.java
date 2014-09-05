package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.Cue;
import cuenation.api.user.domain.User;

import java.util.List;

public interface UserCueRepositoryCustom {

    void markCuesAsViewed(User user, List<String> ids);

    void reSaveCues(User user, List<Cue> cues);

}

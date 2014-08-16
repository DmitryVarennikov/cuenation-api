package cuenation.api.user.service;

import cuenation.api.cue.persistence.UserCueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cuenation.api.user.UserCueController;
import cuenation.api.user.domain.User;

@Service
public class UserCueService {

    @Autowired
    private UserCueRepository userCueRepository;

    public void markCuesAsViewed(User user, UserCueController.PuRequest request) {
        userCueRepository.markCuesAsViewed(user, request.getIds());
    }

}

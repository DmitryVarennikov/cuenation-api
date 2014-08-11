package user.service;

import cue.dao.UserCueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.UserCueController;
import user.domain.User;

@Service
public class UserCueService {

    @Autowired
    private UserCueRepository userCueRepository;

    public void markCuesAsViewed(User user, UserCueController.PuRequest request) {
        userCueRepository.markCuesAsViewed(user, request.getIds());
    }

}

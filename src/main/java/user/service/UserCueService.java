package user.service;

import cue.dao.UserCueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.domain.User;
import user.request.UserViewedCuesRequest;

@Service
public class UserCueService {

    @Autowired
    private UserCueRepository userCueRepository;

    public void markCuesAsViewed(User user, UserViewedCuesRequest request) {
        userCueRepository.markCuesAsViewed(user, request.getIds());
    }

}

package user;

import cue.CueCategory;
import cue.CueCategoryRepository;
import cue.CueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CueService cueService;

    public void setUserCategories(User user, UserCategoryBodyRequest request) {
        List<CueCategory> categories = cueService.findAllByIds(request.getCategoryIds());

        user.setCategories(categories);
        userRepository.save(user);
    }

}

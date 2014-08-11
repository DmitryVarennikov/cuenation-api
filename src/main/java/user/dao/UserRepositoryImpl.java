package user.dao;

import cue.dao.CueCategoryRepository;
import cue.domain.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
import user.domain.User;
import user.request.UserSubscribedCategoriesRequest;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUserCategories(User user, UserSubscribedCategoriesRequest request) {
        List<CueCategory> categories = cueCategoryRepository.findByIdIn(request.getIds());

        user.setCategories(categories);
        userRepository.save(user);
    }
}

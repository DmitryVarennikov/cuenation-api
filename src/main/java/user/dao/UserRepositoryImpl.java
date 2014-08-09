package user.dao;

import cue.dao.CueCategoryRepository;
import cue.domain.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
import user.domain.User;
import user.request.UserCategories;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUserCategories(User user, UserCategories request) {
        List<CueCategory> categories = cueCategoryRepository.findByIdIn(request.getCategoryIds());

        user.setCategories(categories);
        userRepository.save(user);
    }
}

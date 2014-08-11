package user.dao;

import cue.dao.CueCategoryRepository;
import cue.domain.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
import user.UserCategoryController;
import user.domain.User;

import java.util.List;

public class UserRepositoryImpl implements UserRepositoryCustom {

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUserCategories(User user, UserCategoryController.PutRequest request) {
        List<CueCategory> categories = cueCategoryRepository.findByIdIn(request.getIds());

        user.setCategories(categories);
        userRepository.save(user);
    }
}

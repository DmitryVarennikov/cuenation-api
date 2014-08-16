package cuenation.api.user.persistence;

import cuenation.api.cue.persistence.CueCategoryRepository;
import cuenation.api.cue.domain.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
import cuenation.api.user.UserCategoryController;
import cuenation.api.user.domain.User;

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

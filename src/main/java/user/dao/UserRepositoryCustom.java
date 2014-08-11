package user.dao;

import user.UserCategoryController;
import user.domain.User;

public interface UserRepositoryCustom {

    void saveUserCategories(User user, UserCategoryController.PutRequest request);

}

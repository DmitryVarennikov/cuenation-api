package cuenation.api.user.persistence;

import cuenation.api.user.UserCategoryController;
import cuenation.api.user.domain.User;

public interface UserRepositoryCustom {

    void saveUserCategories(User user, UserCategoryController.PutRequest request);

}

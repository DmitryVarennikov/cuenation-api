package cuenation.api.user.persistence;

import cuenation.api.user.UserCueCategoryController;
import cuenation.api.user.domain.User;

public interface UserRepositoryCustom {

    void saveUserCategories(User user, UserCueCategoryController.PutRequest request);

}

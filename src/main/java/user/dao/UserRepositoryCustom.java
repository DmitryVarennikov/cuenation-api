package user.dao;

import user.domain.User;
import user.request.UserSubscribedCategoriesRequest;

public interface UserRepositoryCustom {

    void saveUserCategories(User user, UserSubscribedCategoriesRequest request);

}

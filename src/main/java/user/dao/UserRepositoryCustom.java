package user.dao;

import user.domain.User;
import user.request.UserCategories;

public interface UserRepositoryCustom {

    void saveUserCategories(User user, UserCategories request);

}

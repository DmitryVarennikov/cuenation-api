package user.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import user.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    public User findByToken(String token);

}

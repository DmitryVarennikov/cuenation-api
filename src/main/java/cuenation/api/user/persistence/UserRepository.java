package cuenation.api.user.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import cuenation.api.user.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

    public User findByToken(String token);

}

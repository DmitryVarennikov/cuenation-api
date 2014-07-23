package api;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    public UserEntity findByToken(String token);

}

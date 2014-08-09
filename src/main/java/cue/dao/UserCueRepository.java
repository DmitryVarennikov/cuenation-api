package cue.dao;

import cue.domain.UserCue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCueRepository extends MongoRepository<UserCue, String> {

}

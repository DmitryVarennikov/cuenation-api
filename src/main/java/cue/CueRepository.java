package cue;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CueRepository extends MongoRepository<Cue, String> {

}

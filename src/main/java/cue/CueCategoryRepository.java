package cue;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CueCategoryRepository extends MongoRepository<CueCategory, String> {

    CueCategory findByName(String name);
}

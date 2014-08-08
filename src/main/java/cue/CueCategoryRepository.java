package cue;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CueCategoryRepository extends MongoRepository<CueCategory, String> {

    CueCategory findByName(String name);
}

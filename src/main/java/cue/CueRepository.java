package cue;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CueRepository extends MongoRepository<Cue, String> {

    List<Cue> findByCategoryIn(List<CueCategory> categories);

    List<Cue> findByIdGreaterThanAndCategoryIn(String id, List<CueCategory> categories);

}

package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.CueCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CueRepository extends MongoRepository<Cue, String>, CueRepositoryCustom {

    List<Cue> findByCategoryIn(List<CueCategory> categories);

    List<Cue> findByIdGreaterThanAndCategoryIn(String id, List<CueCategory> categories);

}

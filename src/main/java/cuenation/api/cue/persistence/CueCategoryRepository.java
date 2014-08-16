package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.CueCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface CueCategoryRepository extends MongoRepository<CueCategory, String>, CueCategoryRepositoryCustom {

    CueCategory findByName(String name);

    List<CueCategory> findByIdIn(Collection ids);
}

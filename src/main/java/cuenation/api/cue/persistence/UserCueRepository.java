package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.UserCue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCueRepository extends MongoRepository<UserCue, String>, UserCueRepositoryCustom {

    Page<UserCue> findAllByViewedAtExists(boolean exists, Pageable pageable);

}

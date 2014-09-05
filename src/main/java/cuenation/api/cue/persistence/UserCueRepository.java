package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.domain.UserCue;
import cuenation.api.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserCueRepository extends MongoRepository<UserCue, String>, UserCueRepositoryCustom {

    Page<UserCue> findAllByUser(User user, Pageable pageable);

    Page<UserCue> findAllByUserAndViewedAtExists(User user, boolean exists, Pageable pageable);

    List<UserCue> removeByUserAndCategoryNotIn(User user, List<CueCategory> categories);

}

package cue.dao;

import cue.domain.UserCue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserCueRepository extends MongoRepository<UserCue, String> {

    Page<UserCue> findAllByViewedAtExists(boolean exists, Pageable pageable);

//    List<UserCue> findAllByViewedAtExists(boolean exists, PageRequest pageRequest);

}

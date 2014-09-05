package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.UserCue;
import cuenation.api.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

public class UserCueRepositoryImpl implements UserCueRepositoryCustom {

    @Autowired
    private MongoOperations operations;

    @Override
    public void markCuesAsViewed(User user, List<String> ids) {
        Date now = new Date();

        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user).and("id").in(ids));

        Update update = new Update();
        update.set("viewedAt", now);

        operations.updateMulti(query, update, UserCue.class);
    }

    @Override
    public void reSaveCues(User user, List<Cue> cues) {
        UserCue foundUserCue, newUserCue;

        // retain existing cues and add new ones
        for (Cue cue : cues) {
            Query query = new Query();
            query.addCriteria(Criteria.where("user").is(user).and("cue").in(cue));
            foundUserCue = operations.findOne(query, UserCue.class);

            if (foundUserCue == null) {
                newUserCue = new UserCue(user, cue);
                operations.insert(newUserCue);
            }
        }
    }

}

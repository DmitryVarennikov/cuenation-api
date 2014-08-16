package cuenation.api.cue.persistence;

import cuenation.api.cue.domain.UserCue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import cuenation.api.user.domain.User;

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
        update
                .set("viewedAt", now);
        operations.updateMulti(query, update, UserCue.class);
    }
}

package cuenation.api.cue.persistence;

import com.mongodb.WriteResult;
import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.CueCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CueRepositoryImpl implements CueRepositoryCustom {

    private final Logger logger = LoggerFactory.getLogger(CueRepositoryImpl.class);

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private MongoOperations operations;

    @Override
    public boolean saveIfNotExists(Cue cue) {
        // first make sure the given category exists in our db
        CueCategory cueCategory = cueCategoryRepository.findByName(cue.getCategory().getName());
        if (cueCategory == null) {
            logger.info("Cue category \"{}\" was not found, cue is not saved", cue.getCategory().getName());
            return false;
        }

        cue.setCategory(cueCategory);


        // insert cue if it's not in db yet
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(cue.getTitle()));

        Update update = new Update();
        update
                .set("title", cue.getTitle())
                .set("link", cue.getLink())
                .set("createdAt", cue.getCreatedAt())
                .set("category", cue.getCategory());

        WriteResult writeResult = operations.upsert(query, update, Cue.class);
        // if record was updated we treat it wasn't added anew
        // records are unlikely to be changed, so we basically need to know how many new were added
        return !writeResult.isUpdateOfExisting();
    }

}

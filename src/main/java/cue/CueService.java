package cue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@Service
public class CueService {

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    CueCategoryRepository cueCategoryRepository;

    public void save(Cue cue) {
        // make sure given category exists in db
        CueCategory cueCategory;
        cueCategory = cueCategoryRepository.findByName(cue.getCategory().getName());
        if (!(cueCategory instanceof CueCategory)) {
            cueCategory = cueCategoryRepository.save(cue.getCategory());
        }
        // re-set category but with ID this time to avoid
        // "Cannot create a reference to an object with a NULL id" error
        cue.setCategory(cueCategory);


        // insert cue if it's not in db yet
        Query query = new Query();
        query.addCriteria(Criteria.where("link").is(cue.getLink()));

        Update update = new Update();
        update.set("title", cue.getTitle()).set("link", cue.getLink()).set("category", cue.getCategory());

        mongoOperations.upsert(query, update, Cue.class);
    }

}

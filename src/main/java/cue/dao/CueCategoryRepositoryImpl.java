package cue.dao;

import com.mongodb.WriteResult;
import cue.domain.CueCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CueCategoryRepositoryImpl implements CueCategoryRepositoryCustom {

    @Autowired
    private MongoOperations operations;

    @Override
    public boolean saveIfNotExists(CueCategory category) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(category.getName()));

        Update update = new Update();
        update.set("name", category.getName()).set("link", category.getLink());

        WriteResult writeResult = operations.upsert(query, update, CueCategory.class);
        return !writeResult.isUpdateOfExisting();
    }
}

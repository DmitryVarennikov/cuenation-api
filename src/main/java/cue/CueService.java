package cue;

import com.mongodb.WriteResult;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
public class CueService {

    Logger logger = LoggerFactory.getLogger(CueService.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private RssFetcher fetcher;

    @Autowired
    private RssParser parser;

    @Autowired
    private CategoriesHtmlParser categoriesHtmlParser;

    public int updateCues() {
        int cuesNumber = 0;

        try {
            SyndFeed feed = fetcher.fetch();

            List<Cue> cues = parser.parse(feed);
            for (Cue cue : cues) {
                if (saveCue(cue)) {
                    cuesNumber++;
                }
            }
        } catch (FetcherException | FeedException | IOException e) {
            logger.error(e.getMessage());
        }

        return cuesNumber;
    }

    private boolean saveCue(Cue cue) {
        // first make sure the given category exists in our db
        CueCategory cueCategory = cueCategoryRepository.findByName(cue.getCategory().getName());
        if (cueCategory == null) {
            String message = String.format(
                    "Cue category [%s] was not found, cue is not saved",
                    cue.getCategory().getName());
            logger.error(message);
            return false;
        }

        cue.setCategory(cueCategory);


        // insert cue if it's not in db yet
        Query query = new Query();
        query.addCriteria(Criteria.where("title").is(cue.getTitle()));

        Update update = new Update();
        update.set("title", cue.getTitle()).set("link", cue.getLink()).set("category", cue.getCategory());

        WriteResult writeResult = mongoOperations.upsert(query, update, Cue.class);
        // if record was updated we treat it wasn't added anew
        // records are unlikely to be changed, so we basically need to know how many new were added
        return !writeResult.isUpdateOfExisting();
    }

    public int updateCueCategories() {
        int cueCategoriesNumber = 0;

        List<CueCategory> categories = Collections.EMPTY_LIST;
        try {
            categories = categoriesHtmlParser.parse();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        for (CueCategory category : categories) {
            if (saveCategory(category)) {
                cueCategoriesNumber++;
            }
        }

        return cueCategoriesNumber;
    }

    private boolean saveCategory(CueCategory category) {
        if (category.getLink().length() == 0) {
            throw new IllegalArgumentException("Category link can not be empty");
        }


        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(category.getName()));

        Update update = new Update();
        update.set("name", category.getName()).set("link", category.getLink());

        WriteResult writeResult = mongoOperations.upsert(query, update, CueCategory.class);
        return !writeResult.isUpdateOfExisting();
    }

    public List<CueCategory> findAllByIds(List<String> ids) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(ids));

        return mongoOperations.find(query, CueCategory.class);
    }

}

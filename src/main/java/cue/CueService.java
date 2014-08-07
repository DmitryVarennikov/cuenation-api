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
import java.util.List;

@Service
public class CueService {

    Logger logger = LoggerFactory.getLogger(BgTasks.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private RssFetcher fetcher;

    @Autowired
    private RssParser parser;

    public int updateCues() {
        int cuesNumber = 0;

        try {
            String url = "http://cuenation.com/feed.php";
            SyndFeed feed = fetcher.fetch(url);

            List<Cue> cues = parser.parse(feed);
            for (Cue cue : cues) {
                if (save(cue)) {
                    cuesNumber++;
                }
            }
        } catch (FetcherException | FeedException | IOException e) {
            logger.error(e.getMessage());
        }

        return cuesNumber;
    }

    private boolean save(Cue cue) {
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

        WriteResult writeResult = mongoOperations.upsert(query, update, Cue.class);
        // if record was updated we treat it wasn't added anew
        // records are unlikely to be changed, so we basically need to know how many new were added
        return !writeResult.isUpdateOfExisting();
    }

}

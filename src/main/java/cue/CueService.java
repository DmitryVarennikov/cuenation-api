package cue;

import com.mongodb.WriteResult;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import user.User;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CueService {

    Logger logger = LoggerFactory.getLogger(CueService.class);

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private UserCueRepository userCueRepository;

    @Autowired
    private CueRepository cueRepository;

    @Autowired
    private RssFetcher fetcher;

    @Autowired
    private RssParser parser;

    @Autowired
    private CategoriesHtmlParser categoriesHtmlParser;

    public void updateUserCues(User user) {
        List<CueCategory> subscribedCategories = user.getCategories();

        PageRequest pageRequest = new PageRequest(0, 1, new Sort(Sort.Direction.DESC, "id"));
        Page page = userCueRepository.findAll(pageRequest);
        UserCue recentUserCue = page.getContent().size() > 0 ? (UserCue) page.getContent().get(0) : null;

        List<Cue> freshCues;
        if (recentUserCue != null) {
            freshCues = cueRepository.findByIdGreaterThanAndCategoryIn(recentUserCue.getId(), subscribedCategories);
        } else {
            freshCues = cueRepository.findByCategoryIn(subscribedCategories);
        }

        List<UserCue> userCues = new LinkedList<>();
        for (Cue freshCue : freshCues) {
            userCues.add(new UserCue(user, freshCue));
        }

        System.out.println(userCues);

        mongoOperations.insertAll(userCues);
    }

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
        update
                .set("title", cue.getTitle())
                .set("link", cue.getLink())
                .set("createdAt", cue.getCreatedAt())
                .set("category", cue.getCategory());

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

}

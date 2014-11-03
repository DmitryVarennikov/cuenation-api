package cuenation.api.cue.service;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.persistence.CueCategoryRepository;
import cuenation.api.cue.persistence.CueRepository;
import cuenation.api.cue.persistence.UserCueRepository;
import cuenation.api.user.domain.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

@Service
public class CueService {

//    Logger logger = LoggerFactory.getLogger(CueService.class);

    private static final Log logger = LogFactory.getLog(CueService.class);

    @Autowired
    private UserCueRepository userCueRepository;

    @Autowired
    private CueRepository cueRepository;

    @Autowired
    private CueCategoryRepository cueCategoryRepository;

    @Autowired
    private RssFetcher fetcher;

    @Autowired
    private RssParser parser;

    @Autowired
    private CategoriesHtmlParser categoriesHtmlParser;

    public void updateUserCues(User user) {
        // potentially there may be user cues which don't belong to the new categories
        // How do I know if there are new categories? Actually I don't,
        // that's why we process this clean up upon every request
        userCueRepository.removeByUserAndCategoryNotIn(user, user.getCategories());

        // let's find all the cues not later than a month ago
        // as we don't want to clutter user notifications with a year ago cues
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, -1);
        Date laterThan = calendar.getTime();

        List<Cue> cues = cueRepository.findByCategoryInAndCreatedAtGreaterThan(user.getCategories(), laterThan);
        userCueRepository.reSaveCues(user, cues);
    }

    public int updateCues() {
        int cuesNumber = 0;

        try {
            SyndFeed feed = fetcher.fetch();

            List<Cue> cues = parser.parse(feed);
            for (Cue cue : cues) {
                if (cueRepository.saveIfNotExists(cue)) {
                    cuesNumber++;
                }
            }
        } catch (FetcherException | FeedException | IOException e) {
            logger.error(e.getMessage());
            logger.error(e.getStackTrace());
        }

        return cuesNumber;
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
            if (cueCategoryRepository.saveIfNotExists(category)) {
                cueCategoriesNumber++;
            }
        }

        return cueCategoriesNumber;
    }

}

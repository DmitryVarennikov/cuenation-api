package cuenation.api.cue.service;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import cuenation.api.cue.persistence.CueCategoryNotFoundException;
import cuenation.api.cue.persistence.CueCategoryRepository;
import cuenation.api.cue.persistence.CueRepository;
import cuenation.api.cue.persistence.UserCueRepository;
import cuenation.api.cue.domain.Cue;
import cuenation.api.cue.domain.CueCategory;
import cuenation.api.cue.domain.UserCue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import cuenation.api.user.domain.User;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class CueService {

    Logger logger = LoggerFactory.getLogger(CueService.class);

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

        userCueRepository.save(userCues);
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
        } catch (CueCategoryNotFoundException | FetcherException | FeedException | IOException e) {
            logger.error(e.getMessage());
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

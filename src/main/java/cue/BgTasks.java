package cue;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@EnableScheduling
public class BgTasks {

    Logger logger = LoggerFactory.getLogger(BgTasks.class);

    @Autowired
    CueService cueService;

    @Autowired
    RssFetcher fetcher;

    @Autowired
    RssParser parser;

    // @TODO: update cues db once in 10 minutes
    @Scheduled(cron = "0 */10 * * * *")
    public void updateCues() {
        try {
            String url = "http://cuenation.com/feed.php";
            SyndFeed feed = fetcher.fetch(url);

            List<Cue> cues = parser.parse(feed);
            for (Cue cue : cues) {
                cueService.save(cue);
            }
        } catch (FetcherException | FeedException | IOException e) {
            logger.error(e.getMessage());
        }
    }

}

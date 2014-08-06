package cue;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@EnableScheduling
public class BgTasks {

    @Autowired
    CueRepository repository;

    @Autowired
    RssFetcher fetcher;

    @Autowired
    RssParser parser;

    @Scheduled(cron = "0 * * * * *")
    public void updateCues() {
        // @TODO: update cues db once in 10 minutes

        try {
            String url = "http://cuenation.com/feed.php";
            SyndFeed feed = fetcher.fetch(url);

            List<Cue> cues = parser.parse(feed);
            for (Cue cue : cues) {
                repository.save(cue);
            }
        } catch (FetcherException | FeedException | IOException e) {
            e.printStackTrace();
            // @TODO: log exception
        }
    }

}

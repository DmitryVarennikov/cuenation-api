package cue;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.io.FeedException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class RssFetcher {

    public SyndFeed fetch(String url) throws IOException, FetcherException, FeedException {
        URL feedUrl = new URL(url);

        FeedFetcher feedFetcher = new HttpURLFeedFetcher();
        return feedFetcher.retrieveFeed(feedUrl);
    }

}

package cue;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class RssParser {

    public List<Cue> parse(SyndFeed feed) {
        List<Cue> cues = new LinkedList<>();
        String title, link, category;

        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            title = entry.getTitle();
            link = entry.getLink();
            // @TODO: clean up category value
            category = entry.getCategories().toString();

            cues.add(new Cue(title, link, category));
        }

        return cues;
    }

}

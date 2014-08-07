package cue;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RssParser {

    public List<Cue> parse(SyndFeed feed) {
        List<Cue> cues = new LinkedList<>();
        String title, link, category;
        CueCategory cueCategory;

        for (SyndEntry entry : (List<SyndEntry>) feed.getEntries()) {
            title = entry.getTitle();
            link = entry.getLink();
            category = trimCategory(entry.getCategories().get(0).toString());

            // avoid adding non-full cues
            if (title.length() > 0 && link.length() > 0 && category.length() > 0) {
                cueCategory = new CueCategory(category, "");

                cues.add(new Cue(title, link, cueCategory));
            }
        }

        return cues;
    }

    /**
     * @param input example: "[SyndCategoryImpl.taxonomyUri=null\nSyndCategoryImpl.name=Corsten's Countdown\n]"
     * @return example: "Corsten's Countdown"
     */
    private String trimCategory(String input) {
        String regex = ".*SyndCategoryImpl.name=(.+)\n]$";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        String match = "";
        if (matcher.matches() && matcher.groupCount() > 0) {
            match = matcher.group(1).trim();
        }

        return match;
    }

}

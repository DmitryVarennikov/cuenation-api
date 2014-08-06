package cue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cues")
public class Cue {

    @Id
    private String id;

    private String title;
    private String link;
    private String category;

    public Cue(String title, String link, String category) {
        this.title = title;
        this.link = link;
        this.category = category;
    }

}

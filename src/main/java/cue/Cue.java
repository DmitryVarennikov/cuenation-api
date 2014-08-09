package cue;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cues")
public class Cue {

    @Id
    private String id;

    @Indexed(unique = true)
    private String title;

    private String link;

    private Date createdAt;

    @DBRef
    private CueCategory category;

    public Cue(String title, String link, Date createdAt, CueCategory category) {
        this.title = title;
        this.link = link;
        this.createdAt = createdAt;
        setCategory(category);
    }

    public CueCategory getCategory() {
        return category;
    }

    public void setCategory(CueCategory category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return "Cue{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", category=" + category +
                '}';
    }
}
